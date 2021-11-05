package com.entin.streetmusic.ui.preconcert

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.common.constants.DEFAULT_URL_AVATAR
import com.entin.streetmusic.common.model.music.MusicType
import com.entin.streetmusic.common.model.music.convertToMusicStyle
import com.entin.streetmusic.common.model.vmstate.CommonResponse
import com.entin.streetmusic.util.database.avatar.AvatarModel
import com.entin.streetmusic.util.database.avatar.AvatarRoom
import com.entin.streetmusic.util.firebase.AvatarStorageQueries
import com.entin.streetmusic.util.firebase.ConcertCreateQueries
import com.entin.streetmusic.util.firebase.model.ConcertFirebase
import com.entin.streetmusic.util.time.TimeUtil
import com.entin.streetmusic.util.user.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PreConcertViewModel @Inject constructor(
    private val concertCreateQueries: ConcertCreateQueries,
    private val timeUtil: TimeUtil,
    private val avatarRoom: AvatarRoom,
    private val userPref: UserSession,
    private val storageAvatar: AvatarStorageQueries,
    @Named("AppCoroutine") private val scope: CoroutineScope,
) : ViewModel() {

    /**
     * State Screen
     */
    var statePreConcert: CommonResponse<String> by mutableStateOf(CommonResponse.Initial())
        private set

    /**
     * Fields of PreConcert screen
     */
    // Avatar
    var avatarUrl: String = DEFAULT_URL_AVATAR
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
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                avatarUrl = avatarRoom.getCurrentAvatarUrl(userPref.getId()).avatarUrl
            }
        }

        if (userPref.getStyleMusic().isNotEmpty()) {
            musicType = convertToMusicStyle(userPref.getStyleMusic())
        }

        if (userPref.getBandName().isNotEmpty()) {
            bandName = userPref.getBandName()
        }

        if (userPref.getAddress().isNotEmpty()) {
            address = userPref.getAddress()
        }

        if (userPref.getDescription().isNotEmpty()) {
            description = userPref.getDescription()
        }
    }

    /**
     * Upload new avatar from URI
     */
    fun avatarUploadAndSaveDb(image: Uri?, artistId: String) = viewModelScope.launch {
        statePreConcert = CommonResponse.Load()

        // Save image to Firebase Storage
        if (image != null) {
            uploadAvatar(image, artistId)
        } else {
            statePreConcert = CommonResponse.Error("Image uri is null!")
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
        statePreConcert = CommonResponse.Load()

        if (checkFieldsForEmpty()) {
            val concertToCreate = ConcertFirebase(
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
            concertCreateQueries.createConcert(concertToCreate) { documentId: String ->
                statePreConcert = if (documentId.isEmpty()) {
                    CommonResponse.Error("Failed start concert")
                } else {
                    CommonResponse.Success(documentId)
                }
            }
        } else {
            statePreConcert = CommonResponse.Error("Fill all fields please!")
        }
    }

    /**
     * Upload Avatar
     */
    private fun uploadAvatar(image: Uri, artistId: String) {
        storageAvatar.uploadAvatar(image = image, artistId = artistId) { imageUrl ->
            if (imageUrl.isNotEmpty()) {
                avatarUrl = imageUrl
            }
            // Save in DB
            saveAvatarUrlToDb(artistId, avatarUrl)
            statePreConcert = CommonResponse.Initial()
        }
    }

    /**
     * Check text fields and style music are not empty
     */
    private fun checkFieldsForEmpty(): Boolean =
        bandName.isNotEmpty() && musicType.styleName.isNotEmpty() && address.isNotEmpty() && description.isNotEmpty()

    /**
     * Saving url String into DB
     */
    private fun saveAvatarUrlToDb(artistId: String, imageUrl: String) {
        scope.launch {
            avatarRoom.updateAvatar(
                avatarModel = AvatarModel(
                    artistId = artistId,
                    avatarUrl = imageUrl
                )
            )
        }
    }
}