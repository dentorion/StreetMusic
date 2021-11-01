package com.example.streetmusic.ui.artist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streetmusic.R
import com.example.streetmusic.common.model.domain.ConcertDomain
import com.example.streetmusic.common.model.vmstate.CommonResponse
import com.example.streetmusic.ui.artist.components.ConcertsList
import com.example.streetmusic.ui.artist.components.Header
import com.example.streetmusic.common.theme.StreetMusicTheme

@Composable
fun Artist(
    artistId: String,
    viewModel: ArtistViewModel = hiltViewModel()
) {
    Log.i("MyMusic", "2.Artist")

    ArtistContent(
        state = viewModel.stateArtistScreen,
        getDataOfArtistById = { viewModel.getDataOfArtistById(artistId = artistId) },
        onLikeClick = { viewModel.clickHeart(artistId = artistId) },
    )
}

@Composable
private fun ArtistContent(
    state: CommonResponse<ArtistResponse>,
    getDataOfArtistById: () -> Unit,
    onLikeClick: () -> Unit,
) {
    Log.i("MyMusic", "2.ArtistContent")

    when (state) {
        is CommonResponse.Success -> {
            Log.i("MyMusic", "2.ArtistContent.Success")
            SuccessArtist(state, onLikeClick)
        }
        is CommonResponse.Error -> {
            Log.i("MyMusic", "2.ArtistContent.Error")
            ErrorArtist()
        }
        is CommonResponse.Initial -> {
            Log.i("MyMusic", "2.ArtistContent.Initial")
            InitialArtist(getDataOfArtistById)
        }
        is CommonResponse.Load -> {
            Log.i("MyMusic", "2.ArtistContent.Load")
            LoadingArtist()
        }
    }
}

@Composable
private fun InitialArtist(getDataOfArtistById: () -> Unit) {
    getDataOfArtistById()
}

@Composable
private fun SuccessArtist(
    state: CommonResponse.Success<ArtistResponse>,
    onLikeClick: () -> Unit
) {
    val artistActiveConcertList = state.data.lists.first
    val artistExpiredConcertList = state.data.lists.second
    val artistAvatarUrl = state.data.avatar
    val isFavouriteArtist = state.data.isFavouriteArtist

    /**
     * Decide where band name, city, country should be taken from:
     * Active concert (only one) or last Finished concert
     */
    val actualSourceOfArtistData = if (artistActiveConcertList.isNotEmpty()) {
        artistActiveConcertList
    } else {
        artistExpiredConcertList
    }

    Artist(
        artistActiveConcertDomain = artistActiveConcertList,
        artistExpiredConcertDomain = artistExpiredConcertList,
        isFavouriteArtist = isFavouriteArtist,
        artistOnline = artistActiveConcertList.isNotEmpty(),
        artistName = actualSourceOfArtistData[0].bandName,
        artistAvatar = artistAvatarUrl,
        artistCity = actualSourceOfArtistData[0].city,
        artistCountry = actualSourceOfArtistData[0].country,
        onLikeClick = onLikeClick,
    )
}

@Composable
private fun LoadingArtist() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StreetMusicTheme.colors.artistGradientDown),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = StreetMusicTheme.colors.white)
    }
}

@Composable
private fun ErrorArtist() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StreetMusicTheme.colors.artistGradientDown),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.error), color = StreetMusicTheme.colors.white)
    }
}

@Composable
private fun Artist(
    artistActiveConcertDomain: List<ConcertDomain>,
    artistExpiredConcertDomain: List<ConcertDomain>,
    isFavouriteArtist: Boolean,
    artistOnline: Boolean,
    artistName: String,
    artistAvatar: String,
    artistCity: String,
    artistCountry: String,
    onLikeClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        /**
         * HEADER
         */
        Header(
            artistAvatar = artistAvatar,
            artistOnline = artistOnline,
            artistName = artistName,
            artistCity = artistCity,
            artistCountry = artistCountry,
            isFavouriteArtist = isFavouriteArtist,
            onLikeClick = onLikeClick
        )

        /**
         * Small pink line between Recycler and buttons
         */
        Divider(
            color = StreetMusicTheme.colors.divider,
            thickness = StreetMusicTheme.dimensions.dividerThickness
        )

        /**
         * List of concerts: actual and finished
         */
        ConcertsList(artistActiveConcertDomain, artistExpiredConcertDomain)
    }
}