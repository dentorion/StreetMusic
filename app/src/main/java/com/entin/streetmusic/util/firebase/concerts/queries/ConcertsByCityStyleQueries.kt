package com.entin.streetmusic.util.firebase.concerts.queries

import com.entin.streetmusic.common.model.domain.ConcertDomain
import com.entin.streetmusic.common.model.domain.convertToConcertDomain
import com.entin.streetmusic.common.model.music.MusicType
import com.entin.streetmusic.util.firebase.constant.*
import com.entin.streetmusic.util.time.TimeUtil
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Firebase queries for getting concerts for CityConcerts screen
 */

@ViewModelScoped
class ConcertsByCityStyleQueries @Inject constructor(
    @Named(CONCERTS_NAME_HILT) private val fireBaseDbConcerts: CollectionReference,
    private val timeUtil: TimeUtil,
) {
    private val concerts = mutableListOf<ConcertDomain>()

    /**
     * 1.
     * Get actual "concerts" in city, styles = all
     */
    suspend fun getConcertsActualCity(city: String): List<ConcertDomain> {
        concerts.clear()
        fireBaseDbConcerts
            .whereEqualTo(FIELD_CITY, city)
            .whereEqualTo(FIELD_STOP_MANUAL, false)
            .whereGreaterThan(FIELD_CREATE, Date(timeUtil.getUnixNowMinusHour()))
            .orderBy(FIELD_CREATE, Query.Direction.DESCENDING)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }

    /**
     * 2.1
     * Get expired "concerts" in city BY TIME, styles = all
     * For concerts created less than 1 Hour and manual stop
     */
    suspend fun getConcertsExpiredCityTime(city: String): List<ConcertDomain> {
        concerts.clear()
        fireBaseDbConcerts
            .whereEqualTo(FIELD_CITY, city)
            .whereLessThan(FIELD_CREATE, Date(timeUtil.getUnixNowMinusHour()))
            .whereEqualTo(FIELD_STOP_MANUAL, false)
            .orderBy(FIELD_CREATE, Query.Direction.DESCENDING)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }

    /**
     * 2.2
     * Get expired "concerts" in city BY CANCELLATION, styles = all
     * For concerts that cancelled
     */
    suspend fun getConcertsExpiredCityCancellation(city: String): List<ConcertDomain> {
        concerts.clear()
        fireBaseDbConcerts
            .whereEqualTo(FIELD_CITY, city)
            .whereEqualTo(FIELD_STOP_MANUAL, true)
            .orderBy(FIELD_CREATE, Query.Direction.DESCENDING)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }

    /**
     * 3.
     * Get actual "concerts" by city by style of music
     */
    suspend fun getConcertsActualCityStyle(city: String, type: MusicType): List<ConcertDomain> {
        concerts.clear()
        fireBaseDbConcerts
            .whereEqualTo(FIELD_CITY, city)
            .whereEqualTo(FIELD_STYLE, type.styleName)
            .whereEqualTo(FIELD_STOP_MANUAL, false)
            .whereGreaterThan(FIELD_CREATE, Date(timeUtil.getUnixNowMinusHour()))
            .orderBy(FIELD_CREATE, Query.Direction.DESCENDING)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }

    /**
     * 4.1
     * Get expired "concerts" by city by style of music
     */
    suspend fun getConcertsExpiredCityStyle(city: String, type: MusicType): List<ConcertDomain> {
        concerts.clear()
        fireBaseDbConcerts
            .whereEqualTo(FIELD_CITY, city)
            .whereEqualTo(FIELD_STYLE, type.styleName)
            .whereLessThan(FIELD_CREATE, Date(timeUtil.getUnixNowMinusHour()))
            .whereEqualTo(FIELD_STOP_MANUAL, false)
            .orderBy(FIELD_CREATE, Query.Direction.DESCENDING)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }

    /**
     * 4.2
     * Get CANCELLED "concerts" by city by style of music
     */
    suspend fun getConcertsExpiredCityStyleCancellation(
        city: String,
        type: MusicType
    ): List<ConcertDomain> {
        concerts.clear()
        fireBaseDbConcerts
            .whereEqualTo(FIELD_CITY, city)
            .whereEqualTo(FIELD_STYLE, type.styleName)
            .whereEqualTo(FIELD_STOP_MANUAL, true)
            .orderBy(FIELD_CREATE, Query.Direction.DESCENDING)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }
}