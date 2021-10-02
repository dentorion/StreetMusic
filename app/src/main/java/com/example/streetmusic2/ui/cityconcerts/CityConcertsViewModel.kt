package com.example.streetmusic2.ui.cityconcerts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streetmusic2.common.model.concert.ConcertDomain
import com.example.streetmusic2.common.model.favourite.FavouriteArtist
import com.example.streetmusic2.common.model.music.MusicStyle
import com.example.streetmusic2.common.model.responce.CommonResponse
import com.example.streetmusic2.util.checkfavourite.ArtistsFavouriteRoom
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
    private var switchStyle by mutableStateOf(false)

    // All styles button
    var switchAll by mutableStateOf(true)

    // Finished / Active button
    var switchFA by mutableStateOf(true)

    // Name of selected style button
    var style: MusicStyle by mutableStateOf(MusicStyle.None)

    /**
     * Select one of style buttons
     */
    fun switchStyleFunction(city: String, styleValue: MusicStyle) = viewModelScope.launch {
        switchStyle = true
        switchAll = false
        style = styleValue
        if (switchFA) {
            getConcertsActualCityStyle(city, style)
        } else {
            getConcertsExpiredCityStyle(city, style)
        }
    }

    /**
     * Select Finished / Active button
     */
    fun switchFAFunction(city: String, switchFAValue: Boolean) = viewModelScope.launch {
        switchFA = switchFAValue
        when (switchFA) {
            false -> {
                if (switchAll) {
                    getConcertsExpiredCity(city)
                } else {
                    getConcertsExpiredCityStyle(city, style)
                }
            }
            true -> {
                if (switchAll) {
                    getConcertsActualCity(city)
                } else {
                    getConcertsActualCityStyle(city, style)
                }
            }
        }
    }

    /**
     * Select all styles button
     */
    fun switchAllFunction(city: String) {
        switchAll = true
        switchStyle = false
        style = MusicStyle.None
        if (switchFA) {
            getConcertsActualCity(city)
        } else {
            getConcertsExpiredCity(city)
        }
    }

    /**
     * Get Active concerts of all styles
     */
    fun initialStart(city: String) {
        getConcertsActualCity(city = city)
    }

    /**
     * Save / Del favourite artists
     */
    fun clickHeart(artistId: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            if (artistFavouriteRoom.checkFavouriteById(artistId)) {
                artistFavouriteRoom.delFavourite(artistId = artistId)
            } else {
                artistFavouriteRoom.addFavourite(
                    FavouriteArtist(idArtist = artistId)
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
        val concerts = concertsByCityStyleQueries.getConcertsExpiredCity(city)
        withContext(Dispatchers.IO) {
            concerts.onEach {
                it.isFavourite = artistFavouriteRoom.checkFavouriteById(it.artistId)
            }
        }
        stateConcerts = CommonResponse.Success(concerts)
    }

    /**
     * Get actual "concerts" in city by style of music
     */
    private fun getConcertsActualCityStyle(city: String, style: MusicStyle) = viewModelScope.launch {
        stateConcerts = CommonResponse.Load()
        val concerts = concertsByCityStyleQueries.getConcertsActualCityStyle(city, style)
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
    private fun getConcertsExpiredCityStyle(city: String, style: MusicStyle) = viewModelScope.launch {
        stateConcerts = CommonResponse.Load()
        val concerts = concertsByCityStyleQueries.getConcertsExpiredCityStyle(city, style)
        withContext(Dispatchers.IO) {
            concerts.onEach {
                it.isFavourite = artistFavouriteRoom.checkFavouriteById(it.artistId)
            }
        }
        stateConcerts = CommonResponse.Success(concerts)
    }
}