package com.entin.streetmusic.data.firebase.concerts.queries

import com.entin.streetmusic.data.firebase.constant.CONCERTS_NAME_HILT
import com.entin.streetmusic.data.firebase.constant.FIELD_STOP_MANUAL
import com.entin.streetmusic.data.firebase.constant.FIELD_STOP_TIME
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import javax.inject.Inject
import javax.inject.Named

/**
 * Firebase query for ending concert
 */

class StopConcertQueries @Inject constructor(
    @Named(CONCERTS_NAME_HILT) private val fireBaseDb: CollectionReference
) {

    /**
     * Stop concert manually
     */
    fun stopConcert(
        documentId: String,
        callback: (Boolean) -> Unit
    ) {
        fireBaseDb.document(documentId)
            .update(
                FIELD_STOP_MANUAL, true,
                FIELD_STOP_TIME, FieldValue.serverTimestamp(),
            )
            .addOnSuccessListener {
                callback.invoke(true)
            }.addOnFailureListener {
                callback.invoke(false)
            }
    }
}