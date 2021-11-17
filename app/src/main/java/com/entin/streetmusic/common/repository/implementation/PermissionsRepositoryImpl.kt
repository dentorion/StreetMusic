package com.entin.streetmusic.common.repository.implementation

import android.location.Location
import com.entin.streetmusic.common.repository.implementation.local.LocalSource
import com.entin.streetmusic.common.repository.implementation.remote.RemoteSource
import com.entin.streetmusic.ui.permissions.PermissionsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PermissionsRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource,
) : PermissionsRepository {

    // User Session
    override fun getUserSession() = localSource.userSession()

    // Google Cloud Services - Get Coordinates
    @ExperimentalCoroutinesApi
    override fun getUserLocation(): Flow<Location> =
        remoteSource.googleCloudFunctions().coordinates().requestLocation()

    // Get city name by coordinates of user GPS
    override suspend fun requestCity(location: Location): Flow<Pair<String, String>> =
        remoteSource.googleCloudFunctions().geocoding().requestCity(location)

    // Firebase Functions - UTC Server actual time in MILLISECONDS
    override suspend fun getActualServerUTC(): Flow<Long?> = flow {
        try {
            val result = remoteSource.apiTimeServer().serverUnixTime().get() * MINUTE_IN_MILLISECONDS
            emit(result)
        } catch (e: Exception) {
            emit(null)
        }
    }

    // My utils - Get Time Utilities
    override fun getTimeUtils() =
        remoteSource.apiTimeServer().timeUtil()

    // Permission ViewModel
    override fun saveListenerUserInfo(
        userAddress: Pair<String, String>,
        userLocation: Location,
        timeDifference: Long
    ) {
        getUserSession().apply {
            setCity(userAddress.second)
            setCountry(userAddress.first)
            setLatitude(userLocation.latitude.toString())
            setLongitude(userLocation.longitude.toString())
            setTimeDifference(timeDifference)
        }
    }
}

const val MINUTE_IN_MILLISECONDS = 1000