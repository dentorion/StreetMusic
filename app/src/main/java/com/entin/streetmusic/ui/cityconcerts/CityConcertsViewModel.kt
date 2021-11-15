package com.entin.streetmusic.ui.cityconcerts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.common.model.domain.ConcertDomain
import com.entin.streetmusic.common.model.music.MusicType
import com.entin.streetmusic.common.model.vmstate.CommonResponse
import com.entin.streetmusic.util.firebase.concerts.queries.ConcertsByCityStyleQueries
import com.entin.streetmusic.util.user.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityConcertsViewModel @Inject constructor(
    private val concertsByCityStyleQueries: ConcertsByCityStyleQueries,
    userStateSession: UserSession,
) : ViewModel() {

    // State
    var stateConcerts: CommonResponse<List<ConcertDomain>> by mutableStateOf(CommonResponse.Initial())
        private set

    // Style button on / off state
    private var isMusicTypeChoice by mutableStateOf(false)

    // All styles button state
    var stateAllConcerts by mutableStateOf(true)
        private set

    // Finished / Active button state
    // True -> active
    // False -> expired
    var stateFinishedActiveConcerts by mutableStateOf(true)
        private set

    // Selected style button state
    var stateMusicTypeChoice: MusicType by mutableStateOf(MusicType.None)
        private set

    // On-line concert list for MapObserve
    var onlineConcertsForMap = listOf<ConcertDomain>()
        private set

    // City of the user
    val userCity = userStateSession.getCity()

    /**
     * Initial
     * Get actual "concerts" in city, styles = all
     */
    fun getConcertsActualCity() = viewModelScope.launch {
        isMusicTypeChoice = false
        stateMusicTypeChoice = MusicType.None
        stateAllConcerts = true
        getConcertsActualCityContent()
    }

    /**
     * Select one of style buttons
     */
    fun switchStyle(typeValue: MusicType) = viewModelScope.launch {
        isMusicTypeChoice = true
        stateAllConcerts = false
        stateMusicTypeChoice = typeValue
        if (stateFinishedActiveConcerts) {
            getConcertsActualCityStyle(stateMusicTypeChoice)
        } else {
            getConcertsExpiredCityStyle(stateMusicTypeChoice)
        }
    }

    /**
     * Select Finish / Active button
     */
    fun switchFinishActive(switchFAValue: Boolean) = viewModelScope.launch {
        stateFinishedActiveConcerts = switchFAValue
        when (stateFinishedActiveConcerts) {
            // Expired
            false -> {
                if (stateAllConcerts) {
                    getConcertsExpiredCity()
                } else {
                    getConcertsExpiredCityStyle(stateMusicTypeChoice)
                }
            }
            // Active
            true -> {
                if (stateAllConcerts) {
                    getConcertsActualCityContent()
                } else {
                    getConcertsActualCityStyle(stateMusicTypeChoice)
                }
            }
        }
    }

    /**
     * Select all concerts in City, all styles
     */
    fun switchAllStyles() {
        isMusicTypeChoice = false
        stateMusicTypeChoice = MusicType.None
        stateAllConcerts = true
        if (stateFinishedActiveConcerts) {
            getConcertsActualCityContent()
        } else {
            getConcertsExpiredCity()
        }
    }

    /**
     * Get actual "concerts" in city, style = all
     */
    private fun getConcertsActualCityContent() = viewModelScope.launch {
        stateConcerts = CommonResponse.Load()

        val concerts = concertsByCityStyleQueries.getConcertsActualCity(userCity)

        onlineConcertsForMap = concerts
        concerts.sortedByDescending { it.create }

        stateConcerts = CommonResponse.Success(concerts)
    }

    /**
     * Get expired "concerts" in city, styles = all
     */
    private fun getConcertsExpiredCity() = viewModelScope.launch {
        stateConcerts = CommonResponse.Load()

        val concertsExpiredByTime =
            concertsByCityStyleQueries.getConcertsExpiredCityTime(userCity)

        val concertsExpiredByCancellation =
            concertsByCityStyleQueries.getConcertsExpiredCityCancellation(userCity)

        val concerts = ArrayList<ConcertDomain>().apply {
            addAll(concertsExpiredByTime)
            addAll(concertsExpiredByCancellation)
            sortByDescending { it.create }
        }

        stateConcerts = CommonResponse.Success(concerts)
    }

    /**
     * Get actual "concerts" in city by style of music
     */
    private fun getConcertsActualCityStyle(type: MusicType) = viewModelScope.launch {
        stateConcerts = CommonResponse.Load()

        val concerts = concertsByCityStyleQueries.getConcertsActualCityStyle(userCity, type)
        concerts.sortedByDescending { it.create }

        stateConcerts = CommonResponse.Success(concerts)
    }

    /**
     * Get expired "concerts" in city by style of music
     */
    private fun getConcertsExpiredCityStyle(type: MusicType) = viewModelScope.launch {
        stateConcerts = CommonResponse.Load()

        val concertsExpiredByTime =
            concertsByCityStyleQueries.getConcertsExpiredCityStyle(userCity, type)

        val concertsExpiredByCancellation =
            concertsByCityStyleQueries.getConcertsExpiredCityStyleCancellation(userCity, type)

        val concerts = ArrayList<ConcertDomain>().apply {
            addAll(concertsExpiredByTime)
            addAll(concertsExpiredByCancellation)
            sortByDescending { it.create }
        }

        stateConcerts = CommonResponse.Success(concerts)
    }
}