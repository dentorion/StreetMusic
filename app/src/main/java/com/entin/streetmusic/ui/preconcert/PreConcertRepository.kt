package com.entin.streetmusic.ui.preconcert

import android.net.Uri
import com.entin.streetmusic.util.firebase.concerts.model.ConcertFirebaseModel
import com.entin.streetmusic.util.time.TimeUtil
import com.entin.streetmusic.util.user.UserSession

interface PreConcertRepository {
    // User Session
    fun getUserSession(): UserSession
    // Firebase - Create 'START' Concert
    fun startConcert(concertToCreateModel: ConcertFirebaseModel, callBack: (String) -> Unit)
    // My utils - Get Time Utilities
    fun getTimeUtils(): TimeUtil
    // Firebase STORE - upload new avatar for artist
    fun uploadAvatar(image: Uri, artistId: String, callback: (String) -> Unit)
}