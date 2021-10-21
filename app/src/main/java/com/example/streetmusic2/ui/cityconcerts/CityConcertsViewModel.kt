package com.example.streetmusic2.ui.cityconcerts

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streetmusic2.common.model.domain.ConcertDomain
import com.example.streetmusic2.util.database.checkfavourite.FavouriteArtistModel
import com.example.streetmusic2.common.model.music.MusicType
import com.example.streetmusic2.common.model.viewmodelstate.CommonResponse
import com.example.streetmusic2.util.database.checkfavourite.ArtistsFavouriteRoom
import com.example.streetmusic2.util.firebase.ConcertsByCityStyleQueries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CityConcertsViewModel @Inject constructor(
    private val concertsByCityStyleQueries: ConcertsByCityStyleQueries,
    private val artistFavouriteRoom: ArtistsFavouriteRoom
) : ViewModel() {

    // State
    var stateConcerts: CommonResponse<List<ConcertDomain>> by mutableStateOf(CommonResponse.Initial())
        private set

    // Style button
    private var isMusicTypeChoice by mutableStateOf(false)

    // All styles button
    var stateAllConcerts by mutableStateOf(true)

    // Finished / Active button
    var stateFinishedActiveConcerts by mutableStateOf(true)

    // Name of selected style button
    var stateMusicTypeChoice: MusicType by mutableStateOf(MusicType.None)

    /**
     * Select one of style buttons
     */
    fun switchStyleFunction(city: String, typeValue: MusicType) = viewModelScope.launch {
        isMusicTypeChoice = true
        stateAllConcerts = false
        stateMusicTypeChoice = typeValue
        if (stateFinishedActiveConcerts) {
            getConcertsActualCityStyle(city, stateMusicTypeChoice)
        } else {
            getConcertsExpiredCityStyle(city, stateMusicTypeChoice)
        }
    }

    /**
     * Select Finished / Active button
     */
    fun switchFAFunction(city: String, switchFAValue: Boolean) = viewModelScope.launch {
        stateFinishedActiveConcerts = switchFAValue
        when (stateFinishedActiveConcerts) {
            false -> {
                if (stateAllConcerts) {
                    getConcertsExpiredCity(city)
                } else {
                    getConcertsExpiredCityStyle(city, stateMusicTypeChoice)
                }
            }
            true -> {
                if (stateAllConcerts) {
                    getConcertsActualCity(city)
                } else {
                    getConcertsActualCityStyle(city, stateMusicTypeChoice)
                }
            }
        }
    }

    /**
     * Select all concerts (styles) button
     */
    fun switchAllFunction(city: String) {
        isMusicTypeChoice = false
        stateMusicTypeChoice = MusicType.None
        stateAllConcerts = true
        if (stateFinishedActiveConcerts) {
            getConcertsActualCity(city)
        } else {
            getConcertsExpiredCity(city)
        }
    }

    /**
     * On Initial - Get Active concerts of all styles
     */
    fun initialStart(city: String) {
        getConcertsActualCity(city = city)
    }

    /**
     * Save / Delete favourite artist
     */
    fun clickHeart(artistId: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            if (artistFavouriteRoom.checkFavouriteById(artistId)) {
                artistFavouriteRoom.delFavourite(artistId = artistId)
            } else {
                artistFavouriteRoom.addFavourite(
                    FavouriteArtistModel(idArtist = artistId)
                )
            }
        }
    }

    /**
     * Get actual "concerts" in city, styles = all
     */
    private fun getConcertsActualCity(city: String) = viewModelScope.launch {
        stateConcerts = CommonResponse.Load()
        val concerts = concertsByCityStyleQueries.getConcertsActualCity(city)
        withContext(Dispatchers.IO) {
            concerts.onEach {
                it.isFavourite = artistFavouriteRoom.checkFavouriteById(it.artistId)
            }
        }
        stateConcerts = CommonResponse.Success(concerts)
    }

    /**
     * Get expired "concerts" in city, styles = all
     */
    private fun getConcertsExpiredCity(city: String) = viewModelScope.launch {
        stateConcerts = CommonResponse.Load()
        val concertsExpiredByTime = concertsByCityStyleQueries.getConcertsExpiredCityTime(city)
        Log.i("MyMusic", "CityViewModel. concertsExpiredByTime count: ${concertsExpiredByTime.size}")
        val concertsExpiredByCancellation = concertsByCityStyleQueries.getConcertsExpiredCityCancellation(city)
        Log.i("MyMusic", "CityViewModel. concertsExpiredByCancellation count: ${concertsExpiredByCancellation.size}")

        val concerts = concertsExpiredByTime + concertsExpiredByCancellation

        withContext(Dispatchers.IO) {
            concerts.onEach {
                it.isFavourite = artistFavouriteRoom.checkFavouriteById(it.artistId)
            }.sortedBy { it.create }
        }
        stateConcerts = CommonResponse.Success(concerts)
    }

    /**
     * Get actual "concerts" in city by style of music
     */
    private fun getConcertsActualCityStyle(city: String, type: MusicType) = viewModelScope.launch {
        stateConcerts = CommonResponse.Load()
        val concerts = concertsByCityStyleQueries.getConcertsActualCityStyle(city, type)
        withContext(Dispatchers.IO) {
            concerts.onEach {
                it.isFavourite = artistFavouriteRoom.checkFavouriteById(it.artistId)
            }
        }
        stateConcerts = CommonResponse.Success(concerts)
    }

    /**
     * Get expired "concerts" in city by style of music
     */
    private fun getConcertsExpiredCityStyle(city: String, type: MusicType) = viewModelScope.launch {
        stateConcerts = CommonResponse.Load()

        val concertsExpiredByTime = concertsByCityStyleQueries.getConcertsExpiredCityStyle(city, type)
        Log.i("MyMusic", "CityViewModel. concertsExpiredByTime count: ${concertsExpiredByTime.size}")
        val concertsExpiredByCancellation = concertsByCityStyleQueries.getConcertsExpiredCityStyleCancellation(city, type)
        Log.i("MyMusic", "CityViewModel. concertsExpiredByCancellation count: ${concertsExpiredByCancellation.size}")

        val concerts = concertsExpiredByTime + concertsExpiredByCancellation

        withContext(Dispatchers.IO) {
            concerts.onEach {
                it.isFavourite = artistFavouriteRoom.checkFavouriteById(it.artistId)
            }
        }
        stateConcerts = CommonResponse.Success(concerts)
    }
}