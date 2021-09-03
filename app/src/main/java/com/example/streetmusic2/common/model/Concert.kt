package com.example.streetmusic2.common.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Concert(
    val latitude: String = "0.0",
    val longitude: String = "0.0",
    val country: String = "",
    val city: String = "",

    val id: Int = 0,
    val name: String = "",
    val avatar: String = "",
    val styleMusic: String = "",
    @ServerTimestamp
    val timeStart: Date = Date(),
    @ServerTimestamp
    val timeStop: Date = Date(),
) {
    /**
     * Time passed from the beginning of concert
     */
    val playingTimeMinutes = (Date().time - timeStart.time) / 1000 / 60
    var isFavourite: Boolean = false
}
