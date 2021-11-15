package com.entin.streetmusic.util.firebase.errors.queries

import com.entin.streetmusic.util.firebase.constant.ERROR_NAME_HILT
import com.entin.streetmusic.util.firebase.errors.model.ErrorFirebaseModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Firebase query for starting new concert
 */

@Singleton
class ErrorsQueries @Inject constructor(
    @Named(ERROR_NAME_HILT) private val fireBaseDbErrors: CollectionReference,
) {

    /**
     * Add error report
     */
    fun error(errorFirebase: ErrorFirebaseModel) {
        fireBaseDbErrors.document().set(errorFirebase, SetOptions.merge())
    }
}