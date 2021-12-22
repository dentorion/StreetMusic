package com.entin.streetmusic.core.repository.implementation

import com.entin.streetmusic.core.repository.implementation.local.LocalSource
import com.entin.streetmusic.core.repository.implementation.remote.RemoteSource
import com.entin.streetmusic.ui.screens.authorizate.AuthorizeRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthorizeRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource,
) : AuthorizeRepository {

    // Firebase - get actual artist avatar
    override suspend fun getAvatarUrl(artistId: String, callBack: (String) -> Unit) =
        remoteSource.firebaseDb().avatarFirebase().getAvatarUrl(artistId, callBack)

    // User Session
    override fun getUserSession() = localSource.userSession()

    // Firebase - update artist last entrance
    override fun updateArtistDocument(authUser: FirebaseUser) =
        remoteSource.firebaseDb().artistFirebase().updateArtistDocument(authUser)

    // Firebase - check is artist on-line by Id
    override suspend fun isArtistOnline(artistId: String) =
        remoteSource.firebaseDb().artistFirebase().checkIsArtistOnline(artistId)

    // Authorize ViewModel
    override suspend fun saveArtistUserInfo(user: FirebaseUser?) {
        user?.let {
            // Save userId to userSession
            getAvatarUrl(user.uid) { url ->
                getUserSession().setAvatarUrl(url)
            }

            // Get and save user avatar url to userSession
            updateArtistDocument(authUser = user)

            // Create or Update artist data to firestore database
            getUserSession().setId(user.uid)
        }
    }
}