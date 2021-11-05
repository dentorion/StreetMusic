package com.entin.streetmusic.ui.permissions

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.common.model.domain.ConcertDomain
import com.entin.streetmusic.common.model.vmstate.CommonResponse
import com.entin.streetmusic.util.location.Coordinates
import com.entin.streetmusic.util.location.ReverseGeocoding
import com.entin.streetmusic.util.time.ServerUnixTime
import com.entin.streetmusic.util.time.TimeUtil
import com.entin.streetmusic.util.user.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionsViewModel @Inject constructor(
    private val userLocation: Coordinates,
    private val geoCoding: ReverseGeocoding,
    private val serverUnixTime: ServerUnixTime,
    private val timeUtil: TimeUtil,
    private val userSession: UserSession
) : ViewModel() {

    var state: CommonResponse<ConcertDomain> by mutableStateOf(CommonResponse.Initial())
        private set

    @ExperimentalCoroutinesApi
    fun getUserInfo() = viewModelScope.launch {
        state = CommonResponse.Load()

        // Get Location (longitude and latitude)
        val userLocation: Location = userLocation.requestLocation().first()
        // Get City and Country
        val userAddress = geoCoding.requestCity(userLocation).first()
        // Get correct real Unix Time from world time Server API in MILLISECONDS
        val serverUnixTime = serverUnixTime.get() * 1000
        // Calculate difference between real Unix time gotten from Time Server and
        // application Unix Time in MILLISECONDS
        val timeDifference = timeUtil.calculateDifference(serverUnixTime)
        // Save to userPref in MILLISECONDS
        timeUtil.saveDifference(timeDifference)

        val concertDomain = ConcertDomain(
            latitude = userLocation.latitude.toString(),
            longitude = userLocation.longitude.toString(),
            country = userAddress.first,
            city = userAddress.second,
            UTCDifference = timeDifference,
        )

        userSession.apply {
            setCity(userAddress.second)
            setCountry(userAddress.first)
            setLatitude(userLocation.latitude.toString())
            setLongitude(userLocation.longitude.toString())
            setTimeDifference(timeDifference)
        }

        state = CommonResponse.Success(concertDomain)
    }
}