package com.entin.streetmusic.ui.authorizate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.ui.authorizate.uistate.AuthorizeResponse
import com.entin.streetmusic.util.database.avatar.AvatarModel
import com.entin.streetmusic.util.database.avatar.AvatarRoom
import com.entin.streetmusic.util.firebase.ArtistQueries
import com.entin.streetmusic.util.firebase.AvatarStorageQueries
import com.entin.streetmusic.util.user.UserSession
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

/**
 * Auth ViewModel for Google sign in and Checking if artist is playing now
 */

@HiltViewModel
class AuthorizeViewModel @Inject constructor(
    private val artist: ArtistQueries,
    private val avatarRoom: AvatarRoom,
    private val artistQueries: ArtistQueries,
    private val storageAvatar: AvatarStorageQueries,
    private val userPref: UserSession,
    @Named("AppCoroutine") private val scope: CoroutineScope,
) : ViewModel() {

    var authorizeState: AuthorizeResponse by mutableStateOf(AuthorizeResponse.Initial)
        private set

    /**
     * Google authorization returns Firebase user
     * Set up artist avatar default
     */
    fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        authorizeState = AuthorizeResponse.Load

        val user = Firebase.auth.signInWithCredential(credential).await().user

        withContext(Dispatchers.IO) {
            user?.let { it ->
                /**
                 * Set UserId to UserPref
                 */
                setUserPrefId(it)

                /**
                 * Create or Update artist document of entrance
                 */
                createArtistDocument(it.uid)

                /**
                 * Get Avatar url from Storage by UserId
                 * Yes -> save gotten url
                 * No -> save default url
                 */
                getAnSaveAvatarUrlToDb(artistId = it.uid)

                authorizeState = AuthorizeResponse.Success(user = user)
            }
        }
    }

    /**
     * Check Artist for running concert
     * False -> actual concert is present in Firebase
     * True -> no actual concert in Firebase
     */
    fun checkOnlineConcertByUser(artistId: String) = viewModelScope.launch {
        authorizeState = AuthorizeResponse.Load

        val documentIdConcertOnline = artist.checkArtistOnline(artistId = artistId)
        authorizeState = if (documentIdConcertOnline.isNotEmpty()) {
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

    private suspend fun getAnSaveAvatarUrlToDb(artistId: String) {
        // Default url will be written
        storageAvatar.getAvatarUrl(artistId) { url ->
            scope.launch {
                saveAvatarUrl(url, artistId)
            }
        }
    }

    private suspend fun saveAvatarUrl(avatarUrl: String, artistId: String) {
        val isAvatarUrlInDb = avatarRoom.checkForAvatar(artistId = artistId)

        /**
         * Variants:
         * Storage has file and database has url -> update
         * Storage has file but database hasn't url -> create
         * Storage hasn't file, but database has -> update
         * Storage hasn't file and database hasn't url -> create
         */
        if (avatarUrl.isNotEmpty()) {
            if (isAvatarUrlInDb) {
                // Storage has file and database has url -> update
                avatarRoom.updateAvatar(
                    avatarModel = AvatarModel(
                        artistId = artistId,
                        avatarUrl = avatarUrl
                    )
                )
            } else {
                // Storage has file but database hasn't url -> create
                avatarRoom.setNewAvatarUrl(
                    avatar = AvatarModel(
                        artistId = artistId,
                        avatarUrl = avatarUrl
                    )
                )
            }
        } else {
            if (isAvatarUrlInDb) {
                // Storage hasn't file, but database has -> update
                avatarRoom.updateAvatar(avatarModel = AvatarModel(artistId = artistId))
            } else {
                // Storage hasn't file and database not -> create
                avatarRoom.setNewAvatarUrl(avatar = AvatarModel(artistId = artistId))
            }
        }
    }

    private fun createArtistDocument(uid: String) {
        artistQueries.createArtistDocument(uid = uid)
    }

    private fun setUserPrefId(it: FirebaseUser) {
        userPref.setId(it.toString())
    }
}

