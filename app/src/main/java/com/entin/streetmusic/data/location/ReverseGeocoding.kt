package com.entin.streetmusic.data.location

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.util.Log
import com.entin.streetmusic.BuildConfig
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReverseGeocoding @Inject constructor(@ApplicationContext private val application: Context) {

    suspend fun requestCity(location: Location) = flow {
        Places.initialize(application.applicationContext, BuildConfig.MAPS_API_KEY)
        Places.createClient(application.applicationContext)
        val mGeocoder = Geocoder(application.applicationContext, Locale.ENGLISH)

        try {
            val address = mGeocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            ).first()
            emit(
                Pair(
                    first = address.countryName,
                    second = address.locality
                )
            )
        } catch (e: IOException) {
            Timber.log(Log.ERROR, e.message.toString())
        }
    }.flowOn(Dispatchers.IO)
}