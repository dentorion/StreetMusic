package com.entin.streetmusic.data.firebase.artist.queries

import com.entin.streetmusic.core.model.domain.ConcertDomain
import com.entin.streetmusic.core.model.domain.convertToConcertDomain
import com.entin.streetmusic.data.firebase.artist.model.ArtistFirebaseModel
import com.entin.streetmusic.data.firebase.constant.*
import com.entin.streetmusic.data.time.TimeUtil
import com.entin.streetmusic.data.user.UserSession
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Firebase queries related to Artist user
 */

class ArtistQueries @Inject constructor(
    @Named(CONCERTS_NAME_HILT) private val fireBaseDbConcerts: CollectionReference,
    @Named(USERS_NAME_HILT) private val fireBaseDbUsers: CollectionReference,
    private val timeUtil: TimeUtil,
    private val userPref: UserSession,
) {
    private val concertsActual = mutableListOf<ConcertDomain>()
    private val concertsExpired = mutableListOf<ConcertDomain>()

    /**
     * Check is Artist is online,
     * if yes -> get Firebase document ID of on-line concert for manual stop
     */
    suspend fun checkIsArtistOnline(artistId: String): String {
        var documentId = ""
        fireBaseDbConcerts
            .whereEqualTo(FIELD_ARTIST_ID, artistId)
            .whereEqualTo(FIELD_STOP_MANUAL, false)
            .whereGreaterThan(FIELD_CREATE, Date(timeUtil.getUnixNowMinusHour()))
            .get()
            .await()
            .forEach { document ->
                documentId = document.id
            }
        return documentId
    }

    /**
     * Create Artist Document by UID and count enters
     */
    fun updateArtistDocument(authUser: FirebaseUser) {
        val userModel = ArtistFirebaseModel(
            artistId = authUser.uid,
            city = userPref.getCity(),
            country = userPref.getCountry(),
            createdTime = authUser.metadata?.creationTimestamp
        )
        fireBaseDbUsers.document(authUser.uid).set(userModel, SetOptions.merge())
    }

    /**
     * Get all concerts by Artist
     * Active - first list
     * Not active - second
     */
    suspend fun getAllConcertsByArtist(artistId: String): Pair<List<ConcertDomain>, List<ConcertDomain>> {
        concertsActual.clear()
        concertsExpired.clear()

        fireBaseDbConcerts
            .whereEqualTo(FIELD_ARTIST_ID, artistId)
            .orderBy(FIELD_CREATE, Query.Direction.DESCENDING)
            .get()
            .await()
            .forEach { document ->
                sortConcertByActuality(document)
            }

        return Pair(first = concertsActual, second = concertsExpired)
    }

    /**
     * If concert is not stopped manually, it should be checked
     * by time creation.
     *
     * Active concert = StartTimeFromFirebase > Now - 1 Hour
     * Not active concert = StartTimeFromFirebase < Now - 1 Hour
     * or stopped manually
     */
    private fun sortConcertByActuality(
        document: QueryDocumentSnapshot,
        unixNowMinusHour: Date = Date(timeUtil.getUnixNowMinusHour())
    ) {
        if (document.getBoolean(FIELD_STOP_MANUAL)!!) {
            concertsExpired.add(document.convertToConcertDomain())
        } else {
            if (document.getDate(FIELD_CREATE)!!.after(unixNowMinusHour)) {
                concertsActual.add(document.convertToConcertDomain())
            } else {
                concertsExpired.add(document.convertToConcertDomain())
            }
        }
    }
}