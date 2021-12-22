package com.entin.streetmusic.data.firebase.avatar.model

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

/**
 * FIREBASE Model of CONCERT
 * Every entrance increment counter
 */
data class AvatarFirebaseModel(
    val artistId: String = "",
    val avatarUrl: String = "",
    val timesUpdated: FieldValue = FieldValue.increment(1L),
    @ServerTimestamp
    val updated: Date? = null,
)