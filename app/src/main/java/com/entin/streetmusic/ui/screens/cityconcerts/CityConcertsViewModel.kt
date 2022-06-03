package com.entin.streetmusic.ui.screens.cityconcerts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.core.model.domain.ConcertDomain
import com.entin.streetmusic.core.model.music.MusicType
import com.entin.streetmusic.core.model.response.SMResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityConcertsViewModel @Inject constructor(
    private val repository: CityConcertsRepository,
) : ViewModel() {

    // State
    var uiStateCityConcerts: SMResponse<List<ConcertDomain>> by mutableStateOf(SMResponse.InitialResponse())
        private set

    // Music style button on / off state
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

    // City of the user
    val userCity = repository.getCurrentCity()

    /**
     * Invokes on Initial got by composable function
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
        uiStateCityConcerts = SMResponse.LoadResponse()

        // Get actual concerts of all style music with sorting by create time
        val concerts = repository.getConcertsActualCity(userCity)

        // Save on-line concerts for MapObserve screen
        repository.setOnlineConcerts(concerts)

        uiStateCityConcerts = SMResponse.SuccessResponse(concerts)
    }

    /**
     * Get expired "concerts" in city, styles = all
     */
    private fun getConcertsExpiredCity() = viewModelScope.launch {
        uiStateCityConcerts = SMResponse.LoadResponse()

        val concertsExpiredByTime = repository.getConcertsExpiredCityTime(userCity)

        val concertsExpiredByCancellation = repository.getConcertsExpiredCityCancellation(userCity)

        val concerts = ArrayList<ConcertDomain>().apply {
            addAll(concertsExpiredByTime)
            addAll(concertsExpiredByCancellation)
            sortByDescending { it.create }
        }

        uiStateCityConcerts = SMResponse.SuccessResponse(concerts)
    }

    /**
     * Get actual "concerts" in city by style of music
     * sorted in Repository by Descending
     */
    private fun getConcertsActualCityStyle(type: MusicType) = viewModelScope.launch {
        uiStateCityConcerts = SMResponse.LoadResponse()

        val concerts = repository
            .getConcertsActualCityStyle(userCity, type)

        uiStateCityConcerts = SMResponse.SuccessResponse(concerts)
    }

    /**
     * Get expired "concerts" in city by style of music
     * sorted by Descending in ViewModel because of mixing two lists of concerts
     */
    private fun getConcertsExpiredCityStyle(type: MusicType) = viewModelScope.launch {
        uiStateCityConcerts = SMResponse.LoadResponse()

        val concertsExpiredByTime =
            repository.getConcertsExpiredCityStyle(userCity, type)

        val concertsExpiredByCancellation =
            repository.getConcertsExpiredCityStyleCancellation(userCity, type)

        val concerts = ArrayList<ConcertDomain>().apply {
            addAll(concertsExpiredByTime)
            addAll(concertsExpiredByCancellation)
            sortByDescending { it.create }
        }

        uiStateCityConcerts = SMResponse.SuccessResponse(concerts)
    }
}