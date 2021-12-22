package com.entin.streetmusic.ui.screens.cityconcerts

import com.entin.streetmusic.core.model.domain.ConcertDomain
import com.entin.streetmusic.core.model.music.MusicType

interface CityConcertsRepository {

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

    // Save concerts to show on map as markers
    fun setOnlineConcerts(concerts: List<ConcertDomain>)

    // Get use city by location
    fun getCurrentCity(): String
}