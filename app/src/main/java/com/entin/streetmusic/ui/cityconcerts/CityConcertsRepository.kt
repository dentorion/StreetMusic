package com.entin.streetmusic.ui.cityconcerts

import com.entin.streetmusic.common.model.domain.ConcertDomain
import com.entin.streetmusic.common.model.music.MusicType
import com.entin.streetmusic.util.user.UserSession

interface CityConcertsRepository {
    // User Session
    fun getUserSession(): UserSession

    // Firebase - Get actual concerts in city with all styles of music
    suspend fun getConcertsActualCity(city: String): List<ConcertDomain>

    // Firebase - Get expired concerts in city with all styles of music
    suspend fun getConcertsExpiredCityTime(city: String): List<ConcertDomain>

    // Firebase - Get expired concerts in city with manual cancellation
    suspend fun getConcertsExpiredCityCancellation(city: String): List<ConcertDomain>

    // Firebase - Get actual concerts in city with concrete style of music
    suspend fun getConcertsActualCityStyle(city: String, type: MusicType): List<ConcertDomain>

    // Firebase - Get expired concerts in city with concrete style of music
    suspend fun getConcertsExpiredCityStyle(city: String, type: MusicType): List<ConcertDomain>

    // Firebase - Get expired concerts in city with concrete style of music and with manual cancellation
    suspend fun getConcertsExpiredCityStyleCancellation(
        city: String,
        type: MusicType
    ): List<ConcertDomain>
}