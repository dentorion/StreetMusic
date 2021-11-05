package com.entin.streetmusic.util.firebase

import com.entin.streetmusic.util.firebase.constant.FIELD_STOP_MANUAL
import com.entin.streetmusic.util.firebase.constant.FIELD_STOP_TIME
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Named

@ViewModelScoped
class StopConcertQueries @Inject constructor(
    @Named("concerts") private val fireBaseDb: CollectionReference
) {

    fun stopConcert(documentId: String, callback: (Boolean) -> Unit) {
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