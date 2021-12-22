package com.entin.streetmusic.core.repository.implementation

import android.net.Uri
import com.entin.streetmusic.core.model.music.MusicType
import com.entin.streetmusic.core.model.music.convertToMusicType
import com.entin.streetmusic.core.model.response.RepoResponse
import com.entin.streetmusic.core.repository.implementation.local.LocalSource
import com.entin.streetmusic.core.repository.implementation.remote.RemoteSource
import com.entin.streetmusic.ui.screens.preconcert.PreConcertRepository
import com.entin.streetmusic.data.firebase.concerts.model.ConcertFirebaseModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * [PreConcertRepository] implementation of interface.
 * Getting and saving data to start new concert.
 */

class PreConcertRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource,
) : PreConcertRepository {

    // Firebase - Create 'START' Concert
    @ExperimentalCoroutinesApi
    override suspend fun startConcert() = flow {
        // Set time of concert finish
        localSource.userSession()
            .setTimeStop(remoteSource.apiTimeServer().timeUtil().getUnixNowPlusHour())

        // Create Model of Firebase Concert
        val concertToCreate = ConcertFirebaseModel(
            latitude = localSource.userSession().getLatitude(),
            longitude = localSource.userSession().getLongitude(),
            country = getCurrentCountry(),
            city = getCurrentCity(),
            artistId = localSource.userSession().getId(),
            bandName = getCurrentBandName(),
            styleMusic = getCurrentStyleMusic().styleName,
            address = getCurrentAddress(),
            description = getCurrentDescription(),
            UTCDifference = remoteSource.apiTimeServer().timeUtil()
                .getTimeDifferenceMillisecondsValue()
        )

        remoteSource.firebaseDb().startConcert().createConcert(concertToCreate)
            .collect { response ->
                // Collects RepoResponse<String> from source and emits RepoResponse<String> to ViewModel
                emit(response)
            }
    }

    // Firebase STORE - upload new avatar for artist
    @ExperimentalCoroutinesApi
    override fun uploadAvatar(image: Uri, artistId: String): Flow<RepoResponse<String>> = flow {
        remoteSource.firebaseDb().avatarFirebase().uploadAvatar(image, artistId)
            .collect {
                // Collects RepoResponse<String> from source and emits RepoResponse<String> to ViewModel
                emit(it)
            }
    }

    // Field set - BandName
    override fun setCurrentBandName(value: String) =
        localSource.userSession().setBandName(value)

    // Field set - MusicType
    override fun setCurrentMusicType(value: MusicType) =
        localSource.userSession().setBandName(value.styleName)

    // Field set - Address
    override fun setCurrentAddress(value: String) =
        localSource.userSession().setBandName(value)

    // Field set - Description
    override fun setCurrentDescription(value: String) =
        localSource.userSession().setBandName(value)

    // Set new avatar
    override fun setAvatarUrl(imageUrl: String) =
        localSource.userSession().setAvatarUrl(imageUrl)

    // Field get - BandName
    override fun getCurrentBandName() =
        localSource.userSession().getBandName()

    // Field get - Address
    override fun getCurrentAddress() =
        localSource.userSession().getAddress()

    // Field get - Description
    override fun getCurrentDescription() =
        localSource.userSession().getDescription()

    // Field get - avatar url
    override fun getCurrentAvatarUrl() =
        localSource.userSession().getAvatarUrl()

    // Avatar image get - avatar url
    override fun getCurrentStyleMusic(): MusicType =
        convertToMusicType(localSource.userSession().getStyleMusic())

    // Label get - country
    override fun getCurrentCountry(): String =
        localSource.userSession().getCountry()

    // Label get - city
    override fun getCurrentCity(): String =
        localSource.userSession().getCity()
}