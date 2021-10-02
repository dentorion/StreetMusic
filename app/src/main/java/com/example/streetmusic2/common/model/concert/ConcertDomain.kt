package com.example.streetmusic2.common.model.concert

import com.example.streetmusic2.common.model.music.MusicStyle
import com.example.streetmusic2.common.model.music.convertToMusicStyle
import com.example.streetmusic2.util.constant.*
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.*

/**
 * DOMAIN model of CONCERT
 */
data class ConcertDomain(
    /**
     * Coordinates
     * Base data for listener
     */
    val latitude: String = "0.0",
    val longitude: String = "0.0",
    val country: String = "",
    val city: String = "",

    /**
     * Concert
     * Data for artist
     */
    val artistId: String = "",
    val name: String = "",
    val avatar: String = "",
    val styleMusic: MusicStyle = MusicStyle.None,
    val address: String = "",
    val description: String = "",
    val timeStart: Long = Date().time,
    val timeStop: Long = Date().time + HOUR_ONE_MLS,

    /**
     * Only in Domain model Concert
     */
    var isFavourite: Boolean = false
)

/**
 * Time passed from the beginning of concert
 */
fun ConcertDomain.playingTimeMinutes(): Long {
    return (Date().time - timeStart) / 1000 / 60
}

/**
 * Converting Firebase DocumentSnapshot class to Concert domain model
 */
fun QueryDocumentSnapshot.convertToConcertDomain(): ConcertDomain =
    ConcertDomain(
        artistId = this.getString(FIELD_ARTIST_ID) ?: "",
        name = this.getString(FIELD_NAME_OF_GROUP) ?: "",
        city = this.getString(FIELD_CITY) ?: "",
        country = this.getString(FIELD_COUNTRY) ?: "",
        latitude = this.getString(FIELD_LATITUDE) ?: "",
        longitude = this.getString(FIELD_LONGITUDE) ?: "",
        avatar = this.getString(FIELD_AVATAR) ?: "",
        styleMusic = convertToMusicStyle(this.getString(FIELD_STYLE) ?: ""),
        address = this.getString(FIELD_ADDRESS) ?: "",
        description = this.getString(FIELD_DESCRIPTION) ?: "",
        timeStart = this.getLong(FIELD_START) ?: Date().time,
        timeStop = this.getLong(FIELD_STOP) ?: Date().time + HOUR_ONE_MLS,
    )