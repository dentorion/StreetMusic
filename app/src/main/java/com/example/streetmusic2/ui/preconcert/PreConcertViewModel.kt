package com.example.streetmusic2.ui.preconcert

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streetmusic2.common.model.music.MusicStyle
import com.example.streetmusic2.common.model.responce.CommonResponse
import com.example.streetmusic2.util.firebase.ConcertCreateQueries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreConcertViewModel @Inject constructor(
    private val concertCreateQueries: ConcertCreateQueries
) : ViewModel() {

    /**
     * State
     */
    var statePreConcert: CommonResponse<Nothing?> by mutableStateOf(CommonResponse.Initial())
        private set

    /**
     * Fields of PreConcert page
     */

    // Style
    var musicStyle: MusicStyle by mutableStateOf(MusicStyle.None)

    // Name of Band
    var bandName: String by mutableStateOf("Your Band name")

    // Address
    var address: String by mutableStateOf("Fill address")

    // Description
    var description: String by mutableStateOf("Short description")

    /**
     * Run Concert
     */
    fun runConcert(
        latitude: String,
        longitude: String,
        country: String,
        city: String,
        artistId: String,
        avatar: String,
    ) = viewModelScope.launch {
        if (checkForEmpty()) {
            statePreConcert = CommonResponse.Load()

            concertCreateQueries.createConcert(
                latitude = latitude,
                longitude = longitude,
                country = country,
                city = city,
                artistId = artistId,
                name = bandName,
                avatar = avatar,
                styleMusic = musicStyle,
                address = address,
                description = description,
            ) { answer ->
                statePreConcert = if (answer) {
                    CommonResponse.Success(null)
                } else {
                    CommonResponse.Error("Failed start concert")
                }
            }
        } else {
            statePreConcert = CommonResponse.Error("Fill all fields please!")
        }
    }

    private fun checkForEmpty(): Boolean =
        (bandName.isNotEmpty() && musicStyle.styleName.isNotEmpty()
                && address.isNotEmpty() && description.isNotEmpty())
}