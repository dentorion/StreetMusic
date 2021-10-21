package com.example.streetmusic2.ui.preconcert

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streetmusic2.common.model.music.MusicType
import com.example.streetmusic2.common.model.music.convertToMusicStyle
import com.example.streetmusic2.common.model.viewmodelstate.CommonResponse
import com.example.streetmusic2.util.constant.DEFAULT_URL_AVATAR
import com.example.streetmusic2.util.database.avatar.AvatarModel
import com.example.streetmusic2.util.database.avatar.AvatarRoom
import com.example.streetmusic2.util.firebase.ConcertCreateQueries
import com.example.streetmusic2.util.firebase.model.ConcertFirebase
import com.example.streetmusic2.util.time.TimeUtil
import com.example.streetmusic2.util.user.UserSharedPreferences
import com.google.firebase.storage.FirebaseStorage
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
    private val userPref: UserSharedPreferences,
    @Named("AppCoroutine") private val scope: CoroutineScope,
) : ViewModel() {

    /**
     * State
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

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.i("MyMusic", "userPref.getId() : ${userPref.getId()}")
                avatarUrl = avatarRoom.getCurrentAvatarUrl(userPref.getId()).avatarUrl
            }
        }

        if (userPref.getStyleMusic().isNotEmpty()) {
            musicType = convertToMusicStyle(userPref.getStyleMusic())
        }

        if (userPref.getNameGroup().isNotEmpty()) {
            bandName = userPref.getNameGroup()
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
    fun uploadAvatar(image: Uri?, artistId: String) = viewModelScope.launch {
        statePreConcert = CommonResponse.Load()

        if (image != null) {
            val fileName = "$artistId.jpg"
            val refStorage = FirebaseStorage.getInstance().reference.child("avatars/$fileName")

            refStorage.putFile(image)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        val imageUrl = it.toString()
                        scope.launch {
                            avatarRoom.updateAvatar(
                                avatarModel = AvatarModel(
                                    artistId = artistId,
                                    avatarUrl = imageUrl
                                )
                            )
                        }
                        avatarUrl = imageUrl
                        statePreConcert = CommonResponse.Initial()
                    }
                }
                .addOnFailureListener { e ->
                    print(e.message)
                    statePreConcert = CommonResponse.Error("Can't load new avatar!")
                }
        } else {
            statePreConcert = CommonResponse.Error("Don't make null in photos!")
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

    private fun checkFieldsForEmpty(): Boolean =
        bandName.isNotEmpty() && musicType.styleName.isNotEmpty() && address.isNotEmpty() && description.isNotEmpty()
}