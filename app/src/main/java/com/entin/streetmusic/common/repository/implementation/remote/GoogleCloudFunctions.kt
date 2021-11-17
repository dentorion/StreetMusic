package com.entin.streetmusic.common.repository.implementation.remote

import com.entin.streetmusic.common.repository.interfaces.remote.GoogleCloudFunctions
import com.entin.streetmusic.util.location.Coordinates
import com.entin.streetmusic.util.location.ReverseGeocoding
import javax.inject.Inject

class GoogleCloudFunctions @Inject constructor(
    private val coordinates: Coordinates,
    private val reverseGeocoding: ReverseGeocoding,
) : GoogleCloudFunctions {

    override fun coordinates() = coordinates

    override fun geocoding() = reverseGeocoding
}