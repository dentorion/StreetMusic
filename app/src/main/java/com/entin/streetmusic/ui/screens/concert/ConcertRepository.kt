package com.entin.streetmusic.ui.screens.concert

import com.entin.streetmusic.data.user.UserSession

interface ConcertRepository {
    // User Session
    fun getUserSession(): UserSession
    // Firebase - Manual 'STOP' Concert
    fun stopConcert(documentId: String, callback: (Boolean) -> Unit)
}