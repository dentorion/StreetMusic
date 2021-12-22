package com.entin.streetmusic.ui.screens.preconcert

import android.net.Uri
import com.entin.streetmusic.core.model.music.MusicType
import com.entin.streetmusic.core.model.response.RepoResponse
import kotlinx.coroutines.flow.Flow

interface PreConcertRepository {

    // Firebase - Create 'START' Concert
    suspend fun startConcert(): Flow<RepoResponse<String>>

    // Firebase STORE - upload new avatar for artist
    fun uploadAvatar(image: Uri, artistId: String): Flow<RepoResponse<String>>

    // Field set - BandName
    fun setCurrentMusicType(value: MusicType)

    // Field set - BandName
    fun setCurrentBandName(value: String)

    // Field set - Address
    fun setCurrentAddress(value: String)

    // Field set - Description
    fun setCurrentDescription(value: String)

    // Set new avatar
    fun setAvatarUrl(imageUrl: String)

    // Field get - BandName
    fun getCurrentBandName(): String

    // Field get - Address
    fun getCurrentAddress(): String

    // Field get - Description
    fun getCurrentDescription(): String

    // Field - get avatar url
    fun getCurrentAvatarUrl(): String

    // Field - get avatar url
    fun getCurrentStyleMusic(): MusicType

    // Field - get country
    fun getCurrentCountry(): String

    // Field - get city
    fun getCurrentCity(): String
}