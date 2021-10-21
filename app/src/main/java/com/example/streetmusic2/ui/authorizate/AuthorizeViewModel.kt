package com.example.streetmusic2.ui.authorizate

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streetmusic2.ui.authorizate.state.AuthorizeResponse
import com.example.streetmusic2.util.database.avatar.AvatarModel
import com.example.streetmusic2.util.database.avatar.AvatarRoom
import com.example.streetmusic2.util.firebase.ArtistQueries
import com.example.streetmusic2.util.user.UserSharedPreferences
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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
    private val userPref: UserSharedPreferences,
    @Named("AppCoroutine") private val scope: CoroutineScope,
) : ViewModel() {

    var authorizeState: AuthorizeResponse by mutableStateOf(AuthorizeResponse.Initial)
        private set

    /**
     * Google authorization returns Firebase user
     * Set up artist avatar default
     */
    fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        Log.i("MyMusic", "Authorize VM. signWithCredential")
        authorizeState = AuthorizeResponse.Load

        val user = Firebase.auth.signInWithCredential(credential).await().user

        try {
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
        } catch (e: Exception) {
            authorizeState = AuthorizeResponse.Error(value = e.toString())
        }
    }

    /**
     * Check Artist for running concert
     * False -> actual concert is present in Firebase
     * True -> no actual concert in Firebase
     */
    fun checkOnlineConcertByUser(artistId: String) = viewModelScope.launch {
        Log.i("MyMusic", "Check On-line Concert By User")
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
        Log.i(
            "MyMusic",
            "Check On-line Concert By User. Document is empty: ${documentIdConcertOnline.isEmpty()}"
        )
    }

    private suspend fun getAnSaveAvatarUrlToDb(artistId: String) {
        Log.i("MyMusic", "Authorize VM. setAvatarUrl")
        // Default url will be written
        artistQueries.getAvatarUrl(artistId) { url ->
            scope.launch {
                saveAvatarUrl(url, artistId)
            }
        }
        Log.i("MyMusic", "~ 2. set Avatar Url ok.")
    }

    private suspend fun saveAvatarUrl(avatarUrl: String, artistId: String) {
        val isAvatarUrlInDb = avatarRoom.checkForAvatar(artistId = artistId)
        Log.i("MyMusic", "Base artistId: $artistId")
        Log.i("MyMusic", "Base avatarUrl: $avatarUrl")

        if (avatarUrl.isNotEmpty()) {
            Log.i("MyMusic", "[avatarUrl is Not Empty]")
            if (isAvatarUrlInDb) {
                Log.i("MyMusic", "// Storage has file and database has url -> update")
                // Storage has file and database has url -> update
                avatarRoom.updateAvatar(
                    avatarModel = AvatarModel(
                        artistId = artistId,
                        avatarUrl = avatarUrl,
                    )
                )
            } else {
                Log.i("MyMusic", "[avatarUrl is Not Empty] isAvatar: $isAvatarUrlInDb")
                Log.i("MyMusic", "// Storage has file but database hasn't url -> create")
                // Storage has file but database hasn't url -> create
                avatarRoom.setNewAvatarUrl(
                    avatar = AvatarModel(
                        artistId = artistId,
                        avatarUrl = avatarUrl
                    )
                )
            }
        } else {
            Log.i("MyMusic", "[avatarUrl is Empty]")
            if (isAvatarUrlInDb) {
                Log.i("MyMusic", "[avatarUrl is Empty] isAvatar: $isAvatarUrlInDb")
                Log.i("MyMusic", "// Storage hasn't file, but database has -> update")
                // Storage hasn't file, but database has -> update
                avatarRoom.updateAvatar(
                    avatarModel = AvatarModel(artistId = artistId)
                )
            } else {
                Log.i("MyMusic", "[avatarUrl is Empty] isAvatar: $isAvatarUrlInDb")
                Log.i("MyMusic", "// Storage hasn't file and database not -> create")
                // Storage hasn't file and database not -> create
                avatarRoom.setNewAvatarUrl(
                    avatar = AvatarModel(artistId = artistId)
                )
            }
        }
    }

    private fun createArtistDocument(uid: String) {
        artistQueries.createArtistDocument(uid = uid)
    }

    private fun setUserPrefId(it: FirebaseUser) {
        userPref.setId(it.toString())
        Log.i("MyMusic", "~ 1. UserPref ok.")
    }
}

