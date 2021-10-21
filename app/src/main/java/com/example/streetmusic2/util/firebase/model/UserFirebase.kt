package com.example.streetmusic2.util.firebase.model

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

/**
 * FIREBASE Model of CONCERT
 * Every entrance increment counter
 */
data class UserFirebase(
    val enterCount: FieldValue = FieldValue.increment(1L),
    val country: String = "",
    val city: String = "",
    @ServerTimestamp
    val lastEnter: Date? = null,
)