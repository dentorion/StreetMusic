package com.entin.streetmusic.core.repository.implementation

import com.entin.streetmusic.core.model.domain.ConcertDomain
import com.entin.streetmusic.core.model.map.ShortConcertForMap
import com.entin.streetmusic.core.model.music.MusicType
import com.entin.streetmusic.core.repository.implementation.local.LocalSource
import com.entin.streetmusic.core.repository.implementation.remote.RemoteSource
import com.entin.streetmusic.ui.screens.cityconcerts.CityConcertsRepository
import com.google.gson.Gson
import javax.inject.Inject

class CityConcertsRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource,
) : CityConcertsRepository {

    /**
     * Firebase - Get actual concerts in city with all styles of music
     */
    override suspend fun getConcertsActualCity(city: String): List<ConcertDomain> {
        return remoteSource.firebaseDb().sortedConcerts().getConcertsActualCity(city)
            .sortedByDescending { it.create }
            .toList()
    }

    /**
     * Firebase - Get expired concerts in city with all styles of music
     */
    override suspend fun getConcertsExpiredCityTime(city: String): List<ConcertDomain> =
        remoteSource.firebaseDb().sortedConcerts().getConcertsExpiredCityTime(city)

    /**
     * Firebase - Get expired concerts in city with manual cancellation
     */
    override suspend fun getConcertsExpiredCityCancellation(city: String): List<ConcertDomain> =
        remoteSource.firebaseDb().sortedConcerts().getConcertsExpiredCityCancellation(city)

    /**
     * Firebase - Get actual concerts in city with concrete style of music
     */
    override suspend fun getConcertsActualCityStyle(
        city: String,
        type: MusicType
    ): List<ConcertDomain> =
        remoteSource.firebaseDb().sortedConcerts().getConcertsActualCityStyle(city, type)
            .sortedByDescending { it.create }

    /**
     * Firebase - Get expired concerts in city with concrete style of music
     */
    override suspend fun getConcertsExpiredCityStyle(
        city: String,
        type: MusicType
    ): List<ConcertDomain> =
        remoteSource.firebaseDb().sortedConcerts().getConcertsExpiredCityStyle(city, type)

    /**
     * Firebase - Get expired concerts in city with concrete style of music
     * and with manual cancellation
     */
    override suspend fun getConcertsExpiredCityStyleCancellation(
        city: String,
        type: MusicType
    ): List<ConcertDomain> =
        remoteSource.firebaseDb().sortedConcerts()
            .getConcertsExpiredCityStyleCancellation(city, type)

    /**
     * Save concerts to show on map as markers
     */
    override fun setOnlineConcerts(concerts: List<ConcertDomain>) {
        val shortConcerts: List<ShortConcertForMap> = concerts.map {
            ShortConcertForMap(
                latitude = it.latitude,
                longitude = it.longitude,
                artistId = it.artistId,
                bandName = it.bandName,
                create = it.create.time,
                styleMusic = it.styleMusic.styleName,
            )
        }

        // Save concerts for the map screen
        val concertsGson = Gson().toJson(shortConcerts)
        localSource.userSession().setOnlineConcerts(concertsGson)
    }

    /**
     * Get current country
     */
    override fun getCurrentCity() =
        localSource.userSession().getCity()
}