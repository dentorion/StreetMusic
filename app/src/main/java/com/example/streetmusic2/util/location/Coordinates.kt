package com.example.streetmusic2.util.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.streetmusic2.util.constant.LOCATION_REQUEST_FASTEST_INTERVAL
import com.example.streetmusic2.util.constant.LOCATION_REQUEST_INTERVAL
import com.example.streetmusic2.util.constant.LOCATION_REQUEST_MAX_WAIT_TIME
import com.google.android.gms.location.*
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ViewModelScoped
class Coordinates @Inject constructor(@ApplicationContext private val context: Context) {

    @ExperimentalCoroutinesApi
    fun requestLocation(): Flow<Location> {
        return LocationServices.getFusedLocationProviderClient(context.applicationContext)
            .locationFlow()
    }

    @ExperimentalCoroutinesApi
    fun FusedLocationProviderClient.locationFlow() = callbackFlow<Location> {

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                result ?: return // Ignore null responses
                for (location in result.locations) {
                    try {
                        trySend(location).isSuccess // Send location to the flow
                    } catch (t: Throwable) {
                        Log.i("MyMusic", "Coordinates: Error total")
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
                close(e) // in case of error, close the Flow
            }
        } else {
            Log.i("MyMusic", "Coordinates: Error of permissions")
        }

        // Wait for the consumer to cancel the coroutine and unregister
        // the callback. This suspends the coroutine until the Flow is closed.
        awaitClose {
            removeLocationUpdates(callback)
        }
    }
}