package com.entin.streetmusic.common.repository.implementation

import com.entin.streetmusic.common.model.domain.ConcertDomain
import com.entin.streetmusic.common.model.music.MusicType
import com.entin.streetmusic.common.repository.implementation.local.LocalSource
import com.entin.streetmusic.common.repository.implementation.remote.RemoteSource
import com.entin.streetmusic.ui.cityconcerts.CityConcertsRepository
import javax.inject.Inject

class CityConcertsRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource,
) : CityConcertsRepository {

    // User Session
    override fun getUserSession() = localSource.userSession()

    // Firebase - Get actual concerts in city with all styles of music
    override suspend fun getConcertsActualCity(city: String): List<ConcertDomain> =
        remoteSource.firebaseDb().sortedConcerts().getConcertsActualCity(city)

    // Firebase - Get expired concerts in city with all styles of music
    override suspend fun getConcertsExpiredCityTime(city: String): List<ConcertDomain> =
        remoteSource.firebaseDb().sortedConcerts().getConcertsExpiredCityTime(city)

    // Firebase - Get expired concerts in city with manual cancellation
    override suspend fun getConcertsExpiredCityCancellation(city: String): List<ConcertDomain> =
        remoteSource.firebaseDb().sortedConcerts().getConcertsExpiredCityCancellation(city)

    // Firebase - Get actual concerts in city with concrete style of music
    override suspend fun getConcertsActualCityStyle(
        city: String,
        type: MusicType
    ): List<ConcertDomain> =
        remoteSource.firebaseDb().sortedConcerts().getConcertsActualCityStyle(city, type)

    // Firebase - Get expired concerts in city with concrete style of music
    override suspend fun getConcertsExpiredCityStyle(
        city: String,
        type: MusicType
    ): List<ConcertDomain> =
        remoteSource.firebaseDb().sortedConcerts().getConcertsExpiredCityStyle(city, type)

    // Firebase - Get expired concerts in city with concrete style of music
    // and with manual cancellation
    override suspend fun getConcertsExpiredCityStyleCancellation(
        city: String,
        type: MusicType
    ): List<ConcertDomain> =
        remoteSource.firebaseDb().sortedConcerts()
            .getConcertsExpiredCityStyleCancellation(city, type)
}