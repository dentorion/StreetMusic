package com.example.streetmusic2.ui.artist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streetmusic2.common.model.Concert
import com.example.streetmusic2.common.model.MyResponse
import com.example.streetmusic2.util.firebase.artist.ArtistFirebaseQueries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalPageViewModel @Inject constructor(
    private val artistFirebaseQueries: ArtistFirebaseQueries
) : ViewModel() {

    var stateConcerts: MyResponse<Pair<List<Concert>, List<Concert>>> by mutableStateOf(MyResponse.Initial())
        private set

    /**
     * Get all concerts by artist
     */
    fun getConcertsByArtisId(artistId: Long) = viewModelScope.launch {
        stateConcerts = MyResponse.Load()
        val artistConcerts = artistFirebaseQueries.getConcertsAllByArtist(artistId = artistId)
        stateConcerts = MyResponse.Success(artistConcerts)
    }

}