package com.example.streetmusic2.ui.artist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streetmusic2.common.model.domain.ConcertDomain
import com.example.streetmusic2.common.model.viewmodelstate.CommonResponse
import com.example.streetmusic2.util.firebase.ArtistQueries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val artistQueries: ArtistQueries
) : ViewModel() {

    var stateConcerts: CommonResponse<Pair<List<ConcertDomain>, List<ConcertDomain>>>
            by mutableStateOf(CommonResponse.Initial())
        private set

    /**
     * Get all concerts by artist
     */
    fun getConcertsByArtisId(artistId: String) = viewModelScope.launch {
        stateConcerts = CommonResponse.Load()
        val artistConcerts = artistQueries.getAllConcertsByArtist(artistId = artistId)
        artistConcerts.let {
            stateConcerts = CommonResponse.Success(artistConcerts)
        }
    }
}