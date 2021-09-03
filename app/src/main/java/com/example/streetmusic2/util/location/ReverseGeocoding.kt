package com.example.streetmusic2.util.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import com.example.streetmusic2.BuildConfig
import com.example.streetmusic2.common.model.Concert
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import java.io.IOException
import java.util.*
import javax.inject.Inject

@ViewModelScoped
class ReverseGeocoding @Inject constructor(@ApplicationContext private val application: Context) {
    private lateinit var concert: Concert

    fun requestCity(location: Location): Concert {
        Places.initialize(application.applicationContext, BuildConfig.GMP_KEY)
        Places.createClient(application.applicationContext)
        val mGeocoder = Geocoder(application.applicationContext, Locale.ENGLISH)

        try {
            val addressList: List<Address> = mGeocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )
            concert = Concert(
                latitude = location.latitude.toString(),
                longitude = location.longitude.toString(),
                country = addressList[0].countryName,
                city = addressList[0].locality
            )
        } catch (e: IOException) {
            Log.e("MyMusic", "Unable connect to Geocoder")
        }

        return concert
    }
}