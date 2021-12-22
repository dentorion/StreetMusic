package com.entin.streetmusic.ui.screens.permissions

import android.location.Location
import com.entin.streetmusic.data.time.TimeUtil
import kotlinx.coroutines.flow.Flow

interface PermissionsRepository {

    // Google Cloud Services - Get Coordinates
    fun getUserLocation(): Flow<Location>

    // Get city name by coordinates of user GPS
    suspend fun requestCity(location: Location): Flow<Pair<String, String>>

    // Firebase Functions - UTC Server actual time in MILLISECONDS
    suspend fun getActualServerUTC(): Flow<Long?>

    // My utils - Get Time Utilities
    fun getTimeUtils(): TimeUtil

    // Permission ViewModel
    fun saveUserInfo(
        userAddress: Pair<String, String>,
        userLocation: Location,
        timeDifference: Long
    )
}