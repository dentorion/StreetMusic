package com.example.streetmusic2.util.firebase

import com.example.streetmusic2.common.model.domain.ConcertDomain
import com.example.streetmusic2.common.model.domain.convertToConcertDomain
import com.example.streetmusic2.common.model.music.MusicType
import com.example.streetmusic2.util.constant.FIELD_CITY
import com.example.streetmusic2.util.constant.FIELD_CREATE
import com.example.streetmusic2.util.constant.FIELD_STOP_MANUAL
import com.example.streetmusic2.util.constant.FIELD_STYLE
import com.example.streetmusic2.util.time.TimeUtil
import com.google.firebase.firestore.CollectionReference
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
    @Named("concerts") private val fireBaseDb: CollectionReference,
    private val timeUtil: TimeUtil,
) {
    private val concerts = mutableListOf<ConcertDomain>()

    /**
     * Get actual "concerts" in city, styles = all
     */
    suspend fun getConcertsActualCity(city: String): List<ConcertDomain> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FIELD_CITY, city)
            .whereEqualTo(FIELD_STOP_MANUAL, false)
            .whereGreaterThan(FIELD_CREATE, Date(timeUtil.getUnixNowMinusHour()))
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }

    /**
     * Get expired "concerts" in city BY TIME, styles = all
     * Cancellation : only for concerts created less than 1 Hour and manual stop
     */
    suspend fun getConcertsExpiredCityTime(city: String): List<ConcertDomain> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FIELD_CITY, city)
            .whereLessThan(FIELD_CREATE, Date(timeUtil.getUnixNowMinusHour()))
            .whereEqualTo(FIELD_STOP_MANUAL, false)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }

    /**
     * Get expired "concerts" in city BY CANCELLATION, styles = all
     * Time : only for concerts created more than 1 Hour before
     */
    suspend fun getConcertsExpiredCityCancellation(city: String): List<ConcertDomain> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FIELD_CITY, city)
            .whereEqualTo(FIELD_STOP_MANUAL, true)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }

    /**
     * Get actual "concerts" by city by style of music
     */
    suspend fun getConcertsActualCityStyle(city: String, type: MusicType): List<ConcertDomain> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FIELD_CITY, city)
            .whereEqualTo(FIELD_STYLE, type.styleName)
            .whereEqualTo(FIELD_STOP_MANUAL, false)
            .whereGreaterThan(FIELD_CREATE, Date(timeUtil.getUnixNowMinusHour()))
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }

    /**
     * Get expired "concerts" by city by style of music
     */
    suspend fun getConcertsExpiredCityStyle(city: String, type: MusicType): List<ConcertDomain> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FIELD_CITY, city)
            .whereEqualTo(FIELD_STYLE, type.styleName)
            .whereLessThan(FIELD_CREATE, Date(timeUtil.getUnixNowMinusHour()))
            .whereEqualTo(FIELD_STOP_MANUAL, false)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }

    /**
     * Get CANCELLED "concerts" by city by style of music
     */
    suspend fun getConcertsExpiredCityStyleCancellation(city: String, type: MusicType): List<ConcertDomain> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FIELD_CITY, city)
            .whereEqualTo(FIELD_STYLE, type.styleName)
            .whereEqualTo(FIELD_STOP_MANUAL, true)
            .get()
            .await()
            .forEach { document -> concerts.add(document.convertToConcertDomain()) }
        return concerts.toList()
    }
}