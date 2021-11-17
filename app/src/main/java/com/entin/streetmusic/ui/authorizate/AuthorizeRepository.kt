package com.entin.streetmusic.ui.authorizate

import com.entin.streetmusic.util.user.UserSession
import com.google.firebase.auth.FirebaseUser

interface AuthorizeRepository {
    // Firebase - get actual artist avatar
    suspend fun getAvatarUrl(artistId: String, callBack: (String) -> Unit)

    // User Session
    fun getUserSession(): UserSession

    // Firebase - update artist last entrance
    fun updateArtistDocument(authUser: FirebaseUser)

    // Firebase - check is artist on-line by Id
    suspend fun isArtistOnline(artistId: String): String

    // Authorize ViewModel
    suspend fun saveArtistUserInfo(user: FirebaseUser?)
}