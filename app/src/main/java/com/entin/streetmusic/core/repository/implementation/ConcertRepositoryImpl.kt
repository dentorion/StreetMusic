package com.entin.streetmusic.core.repository.implementation

import com.entin.streetmusic.core.repository.implementation.local.LocalSource
import com.entin.streetmusic.core.repository.implementation.remote.RemoteSource
import com.entin.streetmusic.ui.screens.concert.ConcertRepository
import javax.inject.Inject

class ConcertRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource,
) : ConcertRepository {

    // User Session
    override fun getUserSession() = localSource.userSession()

    // Firebase - Manual 'STOP' Concert
    override fun stopConcert(documentId: String, callback: (Boolean) -> Unit) =
        remoteSource.firebaseDb().stopConcert().stopConcert(documentId, callback)
}