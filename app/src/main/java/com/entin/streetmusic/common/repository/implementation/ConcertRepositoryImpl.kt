package com.entin.streetmusic.common.repository.implementation

import com.entin.streetmusic.common.repository.implementation.local.LocalSource
import com.entin.streetmusic.common.repository.implementation.remote.RemoteSource
import com.entin.streetmusic.ui.concert.ConcertRepository
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