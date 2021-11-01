package com.example.streetmusic.ui.artist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streetmusic.common.model.domain.ConcertDomain
import com.example.streetmusic.common.model.vmstate.CommonResponse
import com.example.streetmusic.common.constant.DEFAULT_URL_AVATAR
import com.example.streetmusic.util.database.checkfavourite.ArtistsFavouriteRoom
import com.example.streetmusic.util.database.checkfavourite.FavouriteArtistModel
import com.example.streetmusic.util.firebase.ArtistQueries
import com.example.streetmusic.util.firebase.AvatarStorageQueries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val artistQueries: ArtistQueries,
    private val artistFavouriteRoom: ArtistsFavouriteRoom,
    private val storageAvatar: AvatarStorageQueries,
) : ViewModel() {

    var stateArtistScreen: CommonResponse<ArtistResponse> by mutableStateOf(CommonResponse.Initial())
        private set

    private var concerts: Pair<List<ConcertDomain>, List<ConcertDomain>> = Pair(listOf(), listOf())

    private var avatarUrl: String = DEFAULT_URL_AVATAR

    private var isFavouriteArtist: Boolean = false

    /**
     * Get all concerts by artist
     */
    fun getDataOfArtistById(artistId: String) = viewModelScope.launch {
        stateArtistScreen = CommonResponse.Load()

        withContext(Dispatchers.Default) {
            // Get all concerts: on-line and off-line
            getConcerts(artistId)

            // Get avatar url
            getAvatarUrl(artistId)

            // Is favourite artist
            getFavouriteState(artistId)

            stateArtistScreen = CommonResponse.Success(
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
            if (artistFavouriteRoom.checkFavouriteById(artistId)) {
                artistFavouriteRoom.delFavourite(artistId = artistId)
            } else {
                artistFavouriteRoom.addFavourite(
                    FavouriteArtistModel(idArtist = artistId)
                )
            }
        }
    }

    private fun getFavouriteState(artistId: String) {
        Log.i("MyMusic", "!!1. getFavouriteState")
        isFavouriteArtist = artistFavouriteRoom.checkFavouriteById(artistId)
    }

    private suspend fun getConcerts(artistId: String) {
        Log.i("MyMusic", "!!2. getConcerts")
        concerts = artistQueries.getAllConcertsByArtist(artistId = artistId)
    }

    private suspend fun getAvatarUrl(artistId: String) {
        Log.i("MyMusic", "!!3. getAvatarUrl")
        storageAvatar.getAvatarUrl(artistId = artistId) { url ->
            if (url.isNotEmpty()) {
                avatarUrl = url
            }

        }
    }
}

data class ArtistResponse(
    val avatar: String,
    val isFavouriteArtist: Boolean,
    val lists: Pair<List<ConcertDomain>, List<ConcertDomain>>,
)