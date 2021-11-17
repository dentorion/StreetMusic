package com.entin.streetmusic.ui.permissions

import android.location.Location
import com.entin.streetmusic.util.time.TimeUtil
import com.entin.streetmusic.util.user.UserSession
import kotlinx.coroutines.flow.Flow

interface PermissionsRepository {
    // User Session
    fun getUserSession(): UserSession

    // Google Cloud Services - Get Coordinates
    fun getUserLocation(): Flow<Location>

    // Get city name by coordinates of user GPS
    suspend fun requestCity(location: Location): Flow<Pair<String, String>>

    // Firebase Functions - UTC Server actual time in MILLISECONDS
    suspend fun getActualServerUTC(): Flow<Long?>

    // My utils - Get Time Utilities
    fun getTimeUtils(): TimeUtil

    // Permission ViewModel
    fun saveListenerUserInfo(
        userAddress: Pair<String, String>,
        userLocation: Location,
        timeDifference: Long
    )
}