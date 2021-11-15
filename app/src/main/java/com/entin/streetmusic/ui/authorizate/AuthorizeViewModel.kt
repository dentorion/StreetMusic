package com.entin.streetmusic.ui.authorizate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.ui.authorizate.uistate.AuthorizeResponse
import com.entin.streetmusic.util.firebase.artist.queries.ArtistQueries
import com.entin.streetmusic.util.firebase.avatar.queries.AvatarQueries
import com.entin.streetmusic.util.user.UserSession
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named


/**
 * Auth ViewModel for Google sign in and Checking if artist is playing now
 */

@HiltViewModel
class AuthorizeViewModel @Inject constructor(
    private val artist: ArtistQueries,
    private val artistQueries: ArtistQueries,
    private val storageAvatar: AvatarQueries,
    private val userSession: UserSession,
    @Named("AppCoroutine") private val scope: CoroutineScope,
) : ViewModel() {

    var uiAuthorizeState: AuthorizeResponse by mutableStateOf(AuthorizeResponse.Initial)
        private set

    /**
     * Link anonymous user with Google account or normal Google auth
     */
    fun linkAnonymousToAccount(credential: AuthCredential) = viewModelScope.launch(Dispatchers.IO) {
        uiAuthorizeState = AuthorizeResponse.Load

        val currentUser = Firebase.auth.currentUser
        if (currentUser != null && currentUser.isAnonymous) {
            currentUser.linkWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    workWithAccount(user)
                } else {
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        signWithCredential(credential)
                    } else {
                        uiAuthorizeState = AuthorizeResponse.Error(task.exception?.message.toString())
                    }
                }
            }
        } else {
            uiAuthorizeState = AuthorizeResponse.Error("User is null")
        }
    }

    /**
     * Google authorization returns Firebase user
     * Set up artist avatar default
     */
    private fun signWithCredential(credential: AuthCredential) =
        scope.launch(Dispatchers.IO) {
            // Authentication with Credential to Google
            Firebase.auth.signInWithCredential(credential).addOnSuccessListener {
                workWithAccount(it.user)
            }.addOnFailureListener {
                uiAuthorizeState = AuthorizeResponse.Error(it.message.toString())
            }
        }

    /**
     * After artist signed in:
     * - save userId to userSession
     * - get and save user avatar url to userSession
     * - update artist data to firestore database
     */
    private fun workWithAccount(user: FirebaseUser?) = viewModelScope.launch(Dispatchers.IO) {
        if (user != null) {
            // Save userId to userSession
            setUserIdToUserSession(user.uid)

            // Get and save user avatar url to userSession
            getAndSaveAvatarUrlToUserSession(artistId = user.uid)

            // Create or Update artist data to firestore database
            createOrUpdateArtistDocument(user)

            uiAuthorizeState = AuthorizeResponse.Success(user = user)
        } else {
            uiAuthorizeState = AuthorizeResponse.Error("AuthorizeViewModel. workWithAccount. USER = NULL")
        }
    }

    /**
     * Check Artist for running concert
     * False -> actual concert is present in Firebase
     * True -> no actual concert in Firebase
     */
    fun checkOnlineConcertByUser(artistId: String) = viewModelScope.launch {
        uiAuthorizeState = AuthorizeResponse.Load

        val documentIdConcertOnline = artist.checkIsArtistOnline(artistId = artistId)
        uiAuthorizeState = if (documentIdConcertOnline.isNotEmpty()) {
            AuthorizeResponse.Navigate(
                artistId = artistId,
                documentId = documentIdConcertOnline
            )
        } else {
            AuthorizeResponse.Navigate(
                artistId = artistId,
                documentId = null
            )
        }
    }

    /**
     * Default url of avatar will be written if Storage hasn't artist avatar
     */
    private suspend fun getAndSaveAvatarUrlToUserSession(artistId: String) {
        storageAvatar.getAvatarUrl(artistId) { url ->
            userSession.setAvatarUrl(url)
        }
    }

    private fun createOrUpdateArtistDocument(user: FirebaseUser?) {
        user?.let {
            artistQueries.updateArtistDocument(authUser = user)
        }
    }

    private fun setUserIdToUserSession(userId: String) {
        Timber.i("Сохранил User Id в user Session: $userId")
        userSession.setId(userId)
    }
}