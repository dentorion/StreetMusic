package com.example.streetmusic2.util.firebase

import com.example.streetmusic2.common.model.concert.ConcertDomain
import com.example.streetmusic2.common.model.concert.ConcertFirebase
import com.example.streetmusic2.common.model.concert.convertToConcertDomain
import com.example.streetmusic2.util.constant.FIELD_ARTIST_ID
import com.example.streetmusic2.util.constant.FIELD_STOP
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

@ViewModelScoped
class ConcertsByArtistQueries @Inject constructor(
    private val fireBaseDb: CollectionReference
) {
    private val concertsActual = mutableListOf<ConcertDomain>()
    private val concertsExpired = mutableListOf<ConcertDomain>()

    /**
     * Get all concerts by Artist
     */
    suspend fun getConcertsAllByArtist(artistId: String): Pair<List<ConcertDomain>,List<ConcertDomain>> {
        concertsActual.clear()
        concertsExpired.clear()
        fireBaseDb
            .whereEqualTo(FIELD_ARTIST_ID, artistId)
            .get()
            .await()
            .forEach { document ->
                if (document.getLong(FIELD_STOP)!! > Date().time) {
                    concertsActual.add(document.convertToConcertDomain())
                } else {
                    concertsExpired.add(document.convertToConcertDomain())
                }
            }
        return Pair(
            first = concertsActual,
            second = concertsExpired
        )
    }
}