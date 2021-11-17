package com.entin.streetmusic.ui.preconcert

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.common.model.music.MusicType
import com.entin.streetmusic.common.model.music.convertToMusicType
import com.entin.streetmusic.common.model.response.StreetMusicResponse
import com.entin.streetmusic.util.firebase.concerts.model.ConcertFirebaseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PreConcertViewModel @Inject constructor(
    private val repository: PreConcertRepository,
) : ViewModel() {

    /**
     * Ui State Screen
     */
    var uiStatePreConcert: StreetMusicResponse<String> by mutableStateOf(StreetMusicResponse.Initial())
        private set

    /**
     * Alert state
     */
    private val _alertState = MutableSharedFlow<Boolean>(replay = 0)
    val alertState: SharedFlow<Boolean> = _alertState

    /**
     * Fields of PreConcert screen
     */
    // Avatar
    var avatarUrl = repository.getUserSession().getAvatarUrl()
        private set

    // Style
    var musicType by mutableStateOf(convertToMusicType(repository.getUserSession().getStyleMusic()))
        private set

    // Name of Band
    var bandName by mutableStateOf(repository.getUserSession().getBandName())
        private set

    // Address
    var address by mutableStateOf(repository.getUserSession().getAddress())
        private set

    // Description
    var description by mutableStateOf(repository.getUserSession().getDescription())
        private set

    // City
    val city = repository.getUserSession().getCity()

    // Country
    val country = repository.getUserSession().getCountry()

    /**
     * Upload new avatar from URI
     */
    fun avatarUpload(image: Uri?, artistId: String) = viewModelScope.launch {
        uiStatePreConcert = StreetMusicResponse.Load()

        // Save image to Firebase Storage
        if (image != null) {
            uploadAvatar(image, artistId)
        } else {
            uiStatePreConcert = StreetMusicResponse.Error("Image uri is null!")
        }
    }

    fun saveCurrentMusicType(it: MusicType) {
        repository.getUserSession().setStyleMusic(it.styleName)
        musicType = it
    }

    fun saveCurrentBandName(it: String) {
        repository.getUserSession().setBandName(it)
        bandName = it
    }

    fun saveCurrentAddress(it: String) {
        repository.getUserSession().setAddress(it)
        address = it
    }

    fun saveCurrentDescription(it: String) {
        repository.getUserSession().setDescription(it)
        description = it
    }

    /**
     * Run Concert
     */
    fun runConcert(
        latitude: String,
        longitude: String,
        country: String,
        city: String,
        artistId: String,
    ) = viewModelScope.launch {
        uiStatePreConcert = StreetMusicResponse.Load()

        if (checkFieldsForEmpty()) {
            val concertToCreate = ConcertFirebaseModel(
                latitude = latitude,
                longitude = longitude,
                country = country,
                city = city,
                artistId = artistId,
                bandName = bandName,
                styleMusic = musicType.styleName,
                address = address,
                description = description,
                UTCDifference = repository.getTimeUtils().getTimeDifferenceMillisecondsValue()
            )

            try {
                repository.startConcert(concertToCreate) { documentId: String ->
                    uiStatePreConcert = if (documentId.isEmpty()) {
                        StreetMusicResponse.Error("Failed start concert")
                    } else {
                        StreetMusicResponse.Success(documentId)
                    }
                }
            } catch (e: Exception) {
                Timber.e("${e.message}")
                uiStatePreConcert = StreetMusicResponse.Error(e.message.toString())
            }
        } else {
            uiStatePreConcert = StreetMusicResponse.Initial()
            _alertState.emit(true)
        }
    }

    /**
     * Upload Avatar and save to UserSession
     */
    private fun uploadAvatar(image: Uri, artistId: String) =
        repository.uploadAvatar(image = image, artistId = artistId) { imageUrl ->
            imageUrl.isNotEmpty().apply {
                avatarUrl = imageUrl
                // Save to UserSession
                saveAvatarUrlToUserSession(avatarUrl)
            }
            uiStatePreConcert = StreetMusicResponse.Initial()
        }

    /**
     * Check text fields and style music are not empty
     */
    private fun checkFieldsForEmpty(): Boolean =
        bandName.isNotEmpty() && musicType.styleName.isNotEmpty()
                && address.isNotEmpty() && description.isNotEmpty()

    /**
     * Saving url String into DB
     */
    private fun saveAvatarUrlToUserSession(imageUrl: String) =
        repository.getUserSession().setAvatarUrl(imageUrl)

}