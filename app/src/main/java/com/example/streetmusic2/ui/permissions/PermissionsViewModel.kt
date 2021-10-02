package com.example.streetmusic2.ui.permissions

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streetmusic2.common.model.concert.ConcertFirebase
import com.example.streetmusic2.common.model.responce.CommonResponse
import com.example.streetmusic2.util.location.Coordinates
import com.example.streetmusic2.util.location.ReverseGeocoding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionsViewModel @Inject constructor(
    private val userLocation: Coordinates,
    private val userCity: ReverseGeocoding
) : ViewModel() {

    var state: CommonResponse<ConcertFirebase> by mutableStateOf(CommonResponse.Initial())
        private set

    @ExperimentalCoroutinesApi
    fun getUserCity() = viewModelScope.launch {
        state = CommonResponse.Load()

        val userLocation: Location = userLocation.requestLocation().first()
        val concert = userCity.requestCity(userLocation)

        state = CommonResponse.Success(concert)

    }
}