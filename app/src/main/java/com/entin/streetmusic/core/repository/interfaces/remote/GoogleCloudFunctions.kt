package com.entin.streetmusic.core.repository.interfaces.remote

import com.entin.streetmusic.data.location.Coordinates
import com.entin.streetmusic.data.location.ReverseGeocoding

interface GoogleCloudFunctions {

    fun coordinates(): Coordinates

    fun geocoding(): ReverseGeocoding
}