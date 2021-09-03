package com.example.streetmusic2.ui.cityconcerts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streetmusic2.common.model.Concert
import com.example.streetmusic2.common.model.FavouriteArtist
import com.example.streetmusic2.common.model.MyResponse
import com.example.streetmusic2.util.checkfavourite.ArtistsFavouriteRoom
import com.example.streetmusic2.util.firebase.ConcertsFirebaseQueries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CityConcertsViewModel @Inject constructor(
    private val concertsFirebaseQueries: ConcertsFirebaseQueries,
    private val artistFavouriteRoom: ArtistsFavouriteRoom
) : ViewModel() {

    var stateConcerts: MyResponse<List<Concert>> by mutableStateOf(MyResponse.Initial())
        private set

    private var switchStyle by mutableStateOf(false)
    var switchAll by mutableStateOf(true)
    var switchFA by mutableStateOf(true)
    var style by mutableStateOf("")

    /**
     * Select one of style buttons
     */
    fun switchStyleFunction(city: String, styleValue: String) = viewModelScope.launch {
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
        style = ""
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
    fun clickHeart(id: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            if (artistFavouriteRoom.checkFavouriteById(id)) {
                artistFavouriteRoom.delFavourite(id = id)
            } else {
                artistFavouriteRoom.addFavourite(
                    FavouriteArtist(
                        idArtist = id
                    )
                )
            }
        }
    }

    /**
     * Get actual "concerts" in city, styles = all
     */
    private fun getConcertsActualCity(city: String) = viewModelScope.launch {
        stateConcerts = MyResponse.Load()
        val concerts = concertsFirebaseQueries.getConcertsActualCity(city)
        withContext(Dispatchers.IO) {
            concerts.onEach {
                it.isFavourite = artistFavouriteRoom.checkFavouriteById(it.id)
            }
        }
        stateConcerts = MyResponse.Success(concerts)
    }

    /**
     * Get expired "concerts" in city, styles = all
     */
    private fun getConcertsExpiredCity(city: String) = viewModelScope.launch {
        stateConcerts = MyResponse.Load()
        val concerts = concertsFirebaseQueries.getConcertsExpiredCity(city)
        withContext(Dispatchers.IO) {
            concerts.onEach {
                it.isFavourite = artistFavouriteRoom.checkFavouriteById(it.id)
            }
        }
        stateConcerts = MyResponse.Success(concerts)
    }

    /**
     * Get actual "concerts" in city by style of music
     */
    private fun getConcertsActualCityStyle(city: String, style: String) = viewModelScope.launch {
        stateConcerts = MyResponse.Load()
        val concerts = concertsFirebaseQueries.getConcertsActualCityStyle(city, style)
        withContext(Dispatchers.IO) {
            concerts.onEach {
                it.isFavourite = artistFavouriteRoom.checkFavouriteById(it.id)
            }
        }
        stateConcerts = MyResponse.Success(concerts)
    }

    /**
     * Get expired "concerts" in city by style of music
     */
    private fun getConcertsExpiredCityStyle(city: String, style: String) = viewModelScope.launch {
        stateConcerts = MyResponse.Load()
        val concerts = concertsFirebaseQueries.getConcertsExpiredCityStyle(city, style)
        withContext(Dispatchers.IO) {
            concerts.onEach {
                it.isFavourite = artistFavouriteRoom.checkFavouriteById(it.id)
            }
        }
        stateConcerts = MyResponse.Success(concerts)
    }
}