package com.entin.streetmusic.util.timber.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

/**
 * Error model for Timber logging to Firebase, Crashlytics
 */
data class ErrorModel(
    val priority: Int? = null,
    val tag: String? = null,
    val message: String? = null,
    val t: Throwable? = null,
    val uId: String? = null,
    val city: String? = null,
    val country: String? = null,
    @ServerTimestamp
    val create: Date? = null,
)