package com.entin.streetmusic.data.firebase.concerts.queries

import com.entin.streetmusic.core.model.response.RepoResponse
import com.entin.streetmusic.data.firebase.concerts.model.ConcertFirebaseModel
import com.entin.streetmusic.data.firebase.constant.CONCERTS_NAME_HILT
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

/**
 * Firebase query for starting new concert
 * Firebase uses KTX library, It means that there is no sense to wrap in flow,
 * and can be used like normal suspend function, but this is practice on pet - project
 */

class StartConcertQueries @Inject constructor(
    @Named(CONCERTS_NAME_HILT) private val fireBaseDb: CollectionReference,
) {
    lateinit var response: RepoResponse<String>

    /**
     * Create concert
     */
    @ExperimentalCoroutinesApi
    fun createConcert(concertToCreateModel: ConcertFirebaseModel): Flow<RepoResponse<String>> =
        flow {
            fireBaseDb.add(concertToCreateModel)
                .addOnSuccessListener { documentInfo ->
                    response = RepoResponse.SuccessResponse(documentInfo.id)
                }
                .addOnFailureListener {
                    response = RepoResponse.ErrorResponse(it.message.toString())
                }.await()

            emit(response)
        }.flowOn(Dispatchers.IO)
}