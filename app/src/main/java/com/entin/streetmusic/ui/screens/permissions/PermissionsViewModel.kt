package com.entin.streetmusic.ui.screens.permissions

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.core.model.domain.ConcertDomain
import com.entin.streetmusic.core.model.response.SMError
import com.entin.streetmusic.core.model.response.SMResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionsViewModel @Inject constructor(
    private val repository: PermissionsRepository,
) : ViewModel() {

    var uiStatePermissions: SMResponse<ConcertDomain> by mutableStateOf(SMResponse.InitialResponse())
        private set

    private var serverUnixTime: Long = 0L

    @ExperimentalCoroutinesApi
    fun getUserInfo() = viewModelScope.launch {
        uiStatePermissions = SMResponse.LoadResponse()

        // Get Location (longitude and latitude)
        val userLocation: Location = repository.getUserLocation().first()

        // Get City and Country
        val userAddress: Pair<String, String> = repository.requestCity(userLocation).first()

        // Get correct real Unix Time from world time Server API in MILLISECONDS
        repository.getActualServerUTC().collect { serverUnixTime = it ?: 0L }

        // Calculate difference between real Unix time gotten from Time Server and
        // application Unix Time in MILLISECONDS
        val timeDifference = repository.getTimeUtils().calculateDifference(serverUnixTime)

        // Save all gotten data in UserSession
        repository.saveUserInfo(userAddress, userLocation, timeDifference)

        uiStatePermissions = if (checkAddress(userAddress)) {
            val concertDomain = ConcertDomain(
                latitude = userLocation.latitude.toString(),
                longitude = userLocation.longitude.toString(),
                country = userAddress.first,
                city = userAddress.second,
                UTCDifference = timeDifference,
            )
            SMResponse.SuccessResponse(concertDomain)
        } else {
            SMResponse.ErrorResponse(SMError.LocationEmpty)
        }
    }

    private fun checkAddress(
        userAddress: Pair<String, String>
    ): Boolean = userAddress.first.isNotEmpty() && userAddress.second.isNotEmpty()
}