package com.example.streetmusic2.util.firebase.artist

import com.example.streetmusic2.common.model.Concert
import com.example.streetmusic2.util.constant.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.getField
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

@ViewModelScoped
class ArtistFirebaseQueries @Inject constructor(
    private val fireBaseDb: CollectionReference
) {
    private val concertsActual = mutableListOf<Concert>()
    private val concertsExpired = mutableListOf<Concert>()

    /**
     * Get all concerts by Artist
     */
    suspend fun getConcertsAllByArtist(artistId: Long): Pair<List<Concert>,List<Concert>> {
        concertsActual.clear()
        concertsExpired.clear()
        fireBaseDb
            .whereEqualTo(FILED_ARTIST_ID, artistId)
            .get()
            .await()
            .forEach {
                if (it.getDate(FILED_STOP)!! > Date()) {
                    concertsActual.add(toDomainModel(it))
                } else {
                    concertsExpired.add(toDomainModel(it))
                }
            }
        return Pair(
            first = concertsActual,
            second = concertsExpired
        )
    }

    private fun toDomainModel(qds: QueryDocumentSnapshot): Concert =
        Concert(
            artistId = qds.getField<Int>(FILED_ARTIST_ID) ?: 0,
            name = qds.getString(FILED_NAME_OF_GROUP) ?: "",
            city = qds.getString(FILED_CITY) ?: "",
            country = qds.getString(FILED_COUNTRY) ?: "",
            latitude = qds.getString(FILED_LATITUDE) ?: "",
            longitude = qds.getString(FILED_LONGITUDE) ?: "",
            avatar = qds.getString(FILED_AVATAR) ?: "",
            styleMusic = qds.getString(FILED_STYLE) ?: "",
            timeStart = qds.getDate(FILED_START) ?: Date(),
            timeStop = qds.getDate(FILED_STOP) ?: Date()
        )
}