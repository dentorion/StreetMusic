package com.entin.streetmusic.common.repository.interfaces.remote

import com.entin.streetmusic.util.location.Coordinates
import com.entin.streetmusic.util.location.ReverseGeocoding

interface GoogleCloudFunctions {

    fun coordinates(): Coordinates

    fun geocoding(): ReverseGeocoding
}