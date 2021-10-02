package com.example.streetmusic2.common.model.concert

import com.example.streetmusic2.util.constant.HOUR_ONE_MLS
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

/**
 * FIREBASE Model of CONCERT
 */
data class ConcertFirebase(
    val latitude: String = "0.0",
    val longitude: String = "0.0",
    val country: String = "",
    val city: String = "",
    val artistId: String = "",
    val name: String = "",
    val avatar: String = "",
    val styleMusic: String = "",
    val address: String = "",
    val description: String = "",
    val timeStart: Long = Date().time,
    val timeStop: Long = Date().time + HOUR_ONE_MLS,
    @ServerTimestamp
    val date: Date = Date(),
    val date2: Long = Date().time
)