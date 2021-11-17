package com.entin.streetmusic.common.repository.implementation

import android.net.Uri
import com.entin.streetmusic.common.repository.implementation.local.LocalSource
import com.entin.streetmusic.common.repository.implementation.remote.RemoteSource
import com.entin.streetmusic.ui.preconcert.PreConcertRepository
import com.entin.streetmusic.util.firebase.concerts.model.ConcertFirebaseModel
import javax.inject.Inject

class PreConcertRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource,
) : PreConcertRepository {

    // User Session
    override fun getUserSession() = localSource.userSession()

    // Firebase - Create 'START' Concert
    override fun startConcert(
        concertToCreateModel: ConcertFirebaseModel,
        callBack: (String) -> Unit
    ) = remoteSource.firebaseDb().startConcert().createConcert(concertToCreateModel, callBack)

    // My utils - Get Time Utilities
    override fun getTimeUtils() =
        remoteSource.apiTimeServer().timeUtil()

    // Firebase STORE - upload new avatar for artist
    override fun uploadAvatar(image: Uri, artistId: String, callback: (String) -> Unit) =
        remoteSource.firebaseDb().avatarFirebase().uploadAvatar(image, artistId, callback)
}