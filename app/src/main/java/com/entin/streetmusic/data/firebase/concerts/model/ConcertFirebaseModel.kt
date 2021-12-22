package com.entin.streetmusic.data.firebase.concerts.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

/**
 * FIREBASE Model of CONCERT
 */
data class ConcertFirebaseModel(
    val latitude: String = "0.0",
    val longitude: String = "0.0",
    val country: String = "",
    val city: String = "",
    val artistId: String = "",
    val bandName: String = "",
    val styleMusic: String = "",
    val address: String = "",
    val description: String = "",
    val UTCDifference: Long,
    @ServerTimestamp
    val create: Date? = null,
    val stopTime: Date? = null,
    val stopManual: Boolean = false,
)