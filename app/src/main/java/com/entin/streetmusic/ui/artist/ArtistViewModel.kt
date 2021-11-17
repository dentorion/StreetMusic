package com.entin.streetmusic.ui.artist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.common.model.domain.ConcertDomain
import com.entin.streetmusic.common.model.response.StreetMusicResponse
import com.entin.streetmusic.util.database.favourite.FavouriteArtistModel
import com.entin.streetmusic.util.firebase.constant.DEFAULT_URL_AVATAR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val repository: ArtistRepository,
) : ViewModel() {

    var uiStateArtist: StreetMusicResponse<ArtistResponse> by mutableStateOf(StreetMusicResponse.Initial())
        private set

    private var concerts: Pair<List<ConcertDomain>, List<ConcertDomain>> = Pair(listOf(), listOf())

    private var avatarUrl: String = DEFAULT_URL_AVATAR

    private var isFavouriteArtist: Boolean = false

    /**
     * Get all concerts by artist
     */
    fun getDataOfArtistById(artistId: String) = viewModelScope.launch {
        uiStateArtist = StreetMusicResponse.Load()

        withContext(Dispatchers.Default) {
            // Get all concerts: on-line and off-line
            getConcerts(artistId)

            // Get avatar url
            getAvatarUrl(artistId)

            // Is favourite artist
            getFavouriteState(artistId)

            uiStateArtist = StreetMusicResponse.Success(
                ArtistResponse(
                    avatar = avatarUrl,
                    isFavouriteArtist = isFavouriteArtist,
                    lists = concerts,
                )
            )
        }
    }

    /**
     * Save / Delete favourite artist
     */
    fun clickHeart(artistId: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            if (repository.isArtistFavourite(artistId)) {
                repository.delFavouriteArtist(artistId = artistId)
            } else {
                repository.addFavouriteArtist(
                    FavouriteArtistModel(idArtist = artistId)
                )
            }
        }
    }

    private fun getFavouriteState(artistId: String) {
        isFavouriteArtist = repository.isArtistFavourite(artistId)
    }

    private suspend fun getConcerts(artistId: String) {
        concerts = repository.getAllConcertsByArtist(artistId = artistId)
    }

    private suspend fun getAvatarUrl(artistId: String) {
        repository.getAvatarUrl(artistId = artistId) { url ->
            avatarUrl = url
        }
    }
}

data class ArtistResponse(
    val avatar: String,
    val isFavouriteArtist: Boolean,
    val lists: Pair<List<ConcertDomain>, List<ConcertDomain>>,
)