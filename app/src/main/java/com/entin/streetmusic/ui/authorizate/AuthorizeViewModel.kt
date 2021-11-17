package com.entin.streetmusic.ui.authorizate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.common.model.response.StreetMusicResponse
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Auth ViewModel for Google sign in and Checking if artist is playing now
 */

@HiltViewModel
class AuthorizeViewModel @Inject constructor(
    private val repository: AuthorizeRepository,
) : ViewModel() {

    var uiStateAuthorize: StreetMusicResponse<InsideResponse> by mutableStateOf(StreetMusicResponse.Initial())
        private set

    /**
     * Link anonymous user with Google account or normal Google auth
     */
    fun linkAnonymousToAccount(credential: AuthCredential) = viewModelScope.launch(Dispatchers.IO) {
        uiStateAuthorize = StreetMusicResponse.Load()

        val currentUser = Firebase.auth.currentUser
        if (currentUser != null && currentUser.isAnonymous) {
            currentUser.linkWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    workWithAccount(task.result?.user)
                } else {
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        signWithCredential(credential)
                    } else {
                        uiStateAuthorize =
                            StreetMusicResponse.Error(task.exception?.message.toString())
                    }
                }
            }
        } else {
            uiStateAuthorize = StreetMusicResponse.Error("User is null")
        }
    }

    /**
     * Google authorization returns Firebase user
     * Set up artist avatar default
     */
    private fun signWithCredential(credential: AuthCredential) =
        // Authentication with Credential to Google
        Firebase.auth.signInWithCredential(credential).addOnSuccessListener {
            workWithAccount(it.user)
        }.addOnFailureListener {
            uiStateAuthorize = StreetMusicResponse.Error(it.message.toString())
        }

    /**
     * After artist signed in:
     * - save userId to userSession
     * - get and save user avatar url to userSession
     * - update artist data to firestore database
     */
    private fun workWithAccount(user: FirebaseUser?) = viewModelScope.launch(Dispatchers.IO) {
        uiStateAuthorize = if (user != null) {
            // Save authorized user data
            repository.saveArtistUserInfo(user)

            StreetMusicResponse.Success(
                InsideResponse(
                    navigateConcert = false,
                    navigatePreConcert = false,
                    artistId = user.uid,
                    documentId = null,
                )
            )
        } else {
            StreetMusicResponse.Error("USER = NULL")
        }
    }

    /**
     * Check Artist for running concert
     * False -> actual concert is present in Firebase
     * True -> no actual concert in Firebase
     */
    fun checkOnlineConcertByUser(artistId: String) = viewModelScope.launch {
        uiStateAuthorize = StreetMusicResponse.Load()

        val documentIdConcertOnline = repository.isArtistOnline(artistId = artistId)
        uiStateAuthorize = if (documentIdConcertOnline.isNotEmpty()) {
            StreetMusicResponse.Success(
                InsideResponse(
                    navigateConcert = true,
                    navigatePreConcert = false,
                    artistId = artistId,
                    documentId = documentIdConcertOnline,
                )
            )
        } else {
            StreetMusicResponse.Success(
                InsideResponse(
                    navigateConcert = false,
                    navigatePreConcert = true,
                    artistId = artistId,
                    documentId = null,
                )
            )
        }
    }
}

data class InsideResponse(
    val artistId: String,
    val navigateConcert: Boolean = false,
    val navigatePreConcert: Boolean = false,
    val documentId: String? = null,
)