package com.entin.streetmusic.util.firebase.artist.model

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

/**
 * FIREBASE Model of CONCERT
 * Every entrance increment counter
 */
data class ArtistFirebaseModel(
    val artistId: String = "",
    val enterCount: FieldValue = FieldValue.increment(1L),
    val country: String = "",
    val city: String = "",
    val createdTime: Long? = null,
    @ServerTimestamp
    val lastEnter: Date? = null,
)