package com.example.streetmusic2.util.firebase

import com.example.streetmusic2.common.model.concert.ConcertDomain
import com.example.streetmusic2.common.model.concert.convertToConcertDomain
import com.example.streetmusic2.common.model.music.MusicStyle
import com.example.streetmusic2.util.constant.FIELD_CITY
import com.example.streetmusic2.util.constant.FIELD_STOP
import com.example.streetmusic2.util.constant.FIELD_STYLE
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

@ViewModelScoped
class ConcertsByCityStyleQueries @Inject constructor(
    private val fireBaseDb: CollectionReference
) {
    private val concerts = mutableListOf<ConcertDomain>()

    /**
     * Get actual "concerts" in city, styles = all
     */
    suspend fun getConcertsActualCity(city: String): List<ConcertDomain> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FIELD_CITY, city)
            .whereGreaterThan(FIELD_STOP, Date().time)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }

    /**
     * Get expired "concerts" in city, styles = all
     */
    suspend fun getConcertsExpiredCity(city: String): List<ConcertDomain> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FIELD_CITY, city)
            .whereLessThan(FIELD_STOP, Date().time)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }

    /**
     * Get actual "concerts" by city by style of music
     */
    suspend fun getConcertsActualCityStyle(city: String, style: MusicStyle): List<ConcertDomain> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FIELD_CITY, city)
            .whereEqualTo(FIELD_STYLE, style.styleName)
            .whereGreaterThan(FIELD_STOP, Date().time)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }

    /**
     * Get expired "concerts" by city by style of music
     */
    suspend fun getConcertsExpiredCityStyle(city: String, style: MusicStyle): List<ConcertDomain> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FIELD_CITY, city)
            .whereEqualTo(FIELD_STYLE, style.styleName)
            .whereLessThan(FIELD_STOP, Date().time)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }
}