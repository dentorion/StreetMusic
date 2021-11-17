package com.entin.streetmusic.util.firebase.concerts.queries

import com.entin.streetmusic.util.firebase.concerts.model.ConcertFirebaseModel
import com.entin.streetmusic.util.firebase.constant.CONCERTS_NAME_HILT
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject
import javax.inject.Named

/**
 * Firebase query for starting new concert
 */

class StartConcertQueries @Inject constructor(
    @Named(CONCERTS_NAME_HILT) private val fireBaseDb: CollectionReference,
) {

    /**
     * Create concert
     */
    fun createConcert(
        concertToCreateModel: ConcertFirebaseModel,
        callBack: (String) -> Unit
    ) {
        fireBaseDb.add(concertToCreateModel)
            .addOnSuccessListener { documentInfo ->
                callBack(documentInfo.id)
            }.addOnFailureListener {
                callBack("")
            }
    }
}