package com.entin.streetmusic.ui.concert

import com.entin.streetmusic.util.user.UserSession

interface ConcertRepository {
    // User Session
    fun getUserSession(): UserSession
    // Firebase - Manual 'STOP' Concert
    fun stopConcert(documentId: String, callback: (Boolean) -> Unit)
}