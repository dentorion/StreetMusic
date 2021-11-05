package com.entin.streetmusic.common.model.domain

import com.entin.streetmusic.common.model.music.MusicType
import com.entin.streetmusic.common.model.music.convertToMusicStyle
import com.entin.streetmusic.util.firebase.constant.*
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
    val latitude: String,
    val longitude: String,
    val country: String,
    val city: String,

    /**
     * Concert
     * Data for artist
     */
    val artistId: String = "",
    val bandName: String = "",
//    val avatar: String = "",
    val styleMusic: MusicType = MusicType.None,
    val address: String = "",
    val description: String = "",
    val create: Date = Date(),

    /**
     * Difference between:
     * Real actual Unix time gotten from Time Server and
     * Application actual Unix Time in MILLISECONDS
     */
    val UTCDifference: Long,

    /**
     * Only in Domain model Concert
     */
    var isFavourite: Boolean = false,
)

/**
 * Converting Firebase DocumentSnapshot class to Concert domain model
 */
fun QueryDocumentSnapshot.convertToConcertDomain(): ConcertDomain =
    ConcertDomain(
        artistId = this.getString(FIELD_ARTIST_ID) ?: "",
        bandName = this.getString(FIELD_NAME_OF_GROUP) ?: "",
        city = this.getString(FIELD_CITY) ?: "",
        country = this.getString(FIELD_COUNTRY) ?: "",
        latitude = this.getString(FIELD_LATITUDE) ?: "",
        longitude = this.getString(FIELD_LONGITUDE) ?: "",
        styleMusic = convertToMusicStyle(this.getString(FIELD_STYLE) ?: "none"),
        address = this.getString(FIELD_ADDRESS) ?: "",
        description = this.getString(FIELD_DESCRIPTION) ?: "",
        create = this.getDate(FIELD_CREATE) ?: Date(),
        UTCDifference = this.getLong("UTCDifference") ?: 0L,
    )