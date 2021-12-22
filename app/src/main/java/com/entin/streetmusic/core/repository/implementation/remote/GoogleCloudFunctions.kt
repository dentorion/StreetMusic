package com.entin.streetmusic.core.repository.implementation.remote

import com.entin.streetmusic.core.repository.interfaces.remote.GoogleCloudFunctions
import com.entin.streetmusic.data.location.Coordinates
import com.entin.streetmusic.data.location.ReverseGeocoding
import javax.inject.Inject

class GoogleCloudFunctions @Inject constructor(
    private val coordinates: Coordinates,
    private val reverseGeocoding: ReverseGeocoding,
) : GoogleCloudFunctions {

    override fun coordinates() = coordinates

    override fun geocoding() = reverseGeocoding
}