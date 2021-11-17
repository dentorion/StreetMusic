package com.entin.streetmusic.util.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.entin.streetmusic.R
import com.entin.streetmusic.common.constants.LOCATION_REQUEST_FASTEST_INTERVAL
import com.entin.streetmusic.common.constants.LOCATION_REQUEST_INTERVAL
import com.entin.streetmusic.common.constants.LOCATION_REQUEST_MAX_WAIT_TIME
import com.google.android.gms.location.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Coordinates @Inject constructor(@ApplicationContext private val context: Context) {

    @ExperimentalCoroutinesApi
    fun requestLocation(): Flow<Location> {
        return LocationServices.getFusedLocationProviderClient(context.applicationContext)
            .locationFlow()
    }

    @SuppressLint("MissingPermission")
    @ExperimentalCoroutinesApi
    fun FusedLocationProviderClient.locationFlow() = callbackFlow<Location> {
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                // Ignore null responses
                result ?: return
                for (location in result.locations) {
                    try {
                        // Send location to the flow
                        trySend(location).isSuccess
                    } catch (t: Throwable) {
                        Timber.log(Log.ERROR, Resources.getSystem().getString(R.string.error_coord))
                    }
                }
            }
        }

        val locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(LOCATION_REQUEST_INTERVAL)
            fastestInterval = TimeUnit.SECONDS.toMillis(LOCATION_REQUEST_FASTEST_INTERVAL)
            maxWaitTime = TimeUnit.SECONDS.toMillis(LOCATION_REQUEST_MAX_WAIT_TIME)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationUpdates(
                locationRequest,
                callback,
                Looper.getMainLooper()
            ).addOnFailureListener { e ->
                // in case of error, close the Flow
                Timber.log(Log.ERROR, Resources.getSystem().getString(R.string.error_permissions2))
                close(e)
            }
        } else {
            Timber.log(Log.ERROR, Resources.getSystem().getString(R.string.error_permissions))
        }

        // Wait for the consumer to cancel the coroutine and unregister
        // the callback. This suspends the coroutine until the Flow is closed.
        awaitClose {
            removeLocationUpdates(callback)
        }
    }
}