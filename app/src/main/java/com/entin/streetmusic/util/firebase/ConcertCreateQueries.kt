package com.entin.streetmusic.util.firebase

import com.entin.streetmusic.util.firebase.model.ConcertFirebase
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Named

/**
 * Firebase query for starting new concert
 */

@ViewModelScoped
class ConcertCreateQueries @Inject constructor(
    @Named("concerts") private val fireBaseDb: CollectionReference,
) {

    /**
     * Create concert
     */
    fun createConcert(
        concertToCreate: ConcertFirebase,
        callBack: (String) -> Unit
    ) {
        fireBaseDb.add(concertToCreate)
            .addOnSuccessListener { documentInfo ->
                callBack(documentInfo.id)
            }.addOnFailureListener {
                callBack("")
            }
    }
}