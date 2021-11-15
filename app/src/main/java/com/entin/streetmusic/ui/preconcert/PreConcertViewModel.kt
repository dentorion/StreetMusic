package com.entin.streetmusic.ui.preconcert

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.common.model.music.MusicType
import com.entin.streetmusic.common.model.music.convertToMusicStyle
import com.entin.streetmusic.common.model.vmstate.CommonResponse
import com.entin.streetmusic.util.firebase.concerts.model.ConcertFirebaseModel
import com.entin.streetmusic.util.firebase.avatar.queries.AvatarQueries
import com.entin.streetmusic.util.firebase.concerts.queries.StartConcertQueries
import com.entin.streetmusic.util.time.TimeUtil
import com.entin.streetmusic.util.user.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PreConcertViewModel @Inject constructor(
    private val startConcertQueries: StartConcertQueries,
    private val timeUtil: TimeUtil,
    private val userSession: UserSession,
    private val storageAvatar: AvatarQueries,
) : ViewModel() {

    /**
     * Ui State Screen
     */
    var uiStatePreConcert: CommonResponse<String> by mutableStateOf(CommonResponse.Initial())
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
    var avatarUrl: String = userSession.getAvatarUrl()
        private set

    // Style
    var musicType: MusicType by mutableStateOf(MusicType.None)

    // Name of Band
    var bandName: String by mutableStateOf("Your Band name")

    // Address
    var address: String by mutableStateOf("Fill address")

    // Description
    var description: String by mutableStateOf("Short description")

    /**
     * Init ViewModel fields value
     */
    init {
        if (userSession.getStyleMusic().isNotEmpty()) {
            musicType = convertToMusicStyle(userSession.getStyleMusic())
        }

        if (userSession.getBandName().isNotEmpty()) {
            bandName = userSession.getBandName()
        }

        if (userSession.getAddress().isNotEmpty()) {
            address = userSession.getAddress()
        }

        if (userSession.getDescription().isNotEmpty()) {
            description = userSession.getDescription()
        }
    }

    /**
     * Upload new avatar from URI
     */
    fun avatarUploadAndSaveDb(image: Uri?, artistId: String) = viewModelScope.launch {
        uiStatePreConcert = CommonResponse.Load()

        // Save image to Firebase Storage
        if (image != null) {
            uploadAvatar(image, artistId)
        } else {
            uiStatePreConcert = CommonResponse.Error("Image uri is null!")
        }
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
        uiStatePreConcert = CommonResponse.Load()

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
                UTCDifference = timeUtil.getTimeDifferenceMillisecondsValue()
            )

            try {
                startConcertQueries.createConcert(concertToCreate) { documentId: String ->
                    uiStatePreConcert = if (documentId.isEmpty()) {
                        CommonResponse.Error("Failed start concert")
                    } else {
                        CommonResponse.Success(documentId)
                    }
                }
            } catch (e: Exception) {
                Timber.i("${e.message}")
            }
        } else {
            uiStatePreConcert = CommonResponse.Initial()
            _alertState.emit(true)
        }
    }

    /**
     * Upload Avatar
     */
    private fun uploadAvatar(image: Uri, artistId: String) {
        storageAvatar.uploadAvatar(image = image, artistId = artistId) { imageUrl ->
            imageUrl.isNotEmpty().apply {
                avatarUrl = imageUrl
                // Save to UserSession
                saveAvatarUrlToUserSession(avatarUrl)
            }
            uiStatePreConcert = CommonResponse.Initial()
        }
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
    private fun saveAvatarUrlToUserSession(imageUrl: String) {
        userSession.setAvatarUrl(imageUrl)
    }
}