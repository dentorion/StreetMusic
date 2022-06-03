package com.entin.streetmusic.core.repository.implementation

import android.location.Location
import com.entin.streetmusic.core.repository.implementation.local.LocalSource
import com.entin.streetmusic.core.repository.implementation.remote.RemoteSource
import com.entin.streetmusic.ui.screens.permissions.PermissionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PermissionsRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource,
) : PermissionsRepository {

    // Google Cloud Services - Get Coordinates

    @OptIn(ExperimentalCoroutinesApi::class)
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
    }.flowOn(Dispatchers.IO)

    // My utils - Get Time Utilities
    override fun getTimeUtils() =
        remoteSource.apiTimeServer().timeUtil()

    // Permission ViewModel
    override fun saveUserInfo(
        userAddress: Pair<String, String>,
        userLocation: Location,
        timeDifference: Long
    ) {
        localSource.userSession().apply {
            setCity(userAddress.second)
            setCountry(userAddress.first)
            setLatitude(userLocation.latitude.toString())
            setLongitude(userLocation.longitude.toString())
            setTimeDifference(timeDifference)
        }
    }
}

const val MINUTE_IN_MILLISECONDS = 1000