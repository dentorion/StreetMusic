package com.example.streetmusic.util.firebase

import android.util.Log
import com.example.streetmusic.common.model.domain.ConcertDomain
import com.example.streetmusic.common.model.domain.convertToConcertDomain
import com.example.streetmusic.common.constant.FIELD_ARTIST_ID
import com.example.streetmusic.common.constant.FIELD_CREATE
import com.example.streetmusic.common.constant.FIELD_STOP_MANUAL
import com.example.streetmusic.util.firebase.model.UserFirebase
import com.example.streetmusic.util.time.TimeUtil
import com.example.streetmusic.util.user.UserSession
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Firebase queries related to Artist user
 */

@ViewModelScoped
class ArtistQueries @Inject constructor(
    @Named("concerts") private val fireBaseDbConcerts: CollectionReference,
    @Named("users") private val fireBaseDbUsers: CollectionReference,
    private val timeUtil: TimeUtil,
    private val userPref: UserSession,
) {
    private val concertsActual = mutableListOf<ConcertDomain>()
    private val concertsExpired = mutableListOf<ConcertDomain>()

    /**
     * Check is Artist is online,
     * if yes -> get Firebase document ID of on-line concert for manual stop
     */
    suspend fun checkArtistOnline(artistId: String): String {
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
    fun createArtistDocument(uid: String) {
        val user = UserFirebase(
            city = userPref.getCity(),
            country = userPref.getCountry()
        )
        fireBaseDbUsers.document(uid).set(user, SetOptions.merge())
        Log.i("MyMusic", "~ 3. Create Artist Document ok.")
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