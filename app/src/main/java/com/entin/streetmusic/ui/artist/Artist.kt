package com.entin.streetmusic.ui.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.entin.streetmusic.R
import com.entin.streetmusic.common.model.domain.ConcertDomain
import com.entin.streetmusic.common.model.vmstate.CommonResponse
import com.entin.streetmusic.common.theme.StreetMusicTheme
import com.entin.streetmusic.ui.artist.components.ConcertsList
import com.entin.streetmusic.ui.artist.components.Header
import com.entin.streetmusic.ui.start.components.BackgroundImage
import timber.log.Timber

@ExperimentalCoilApi
@Composable
fun Artist(
    artistId: String,
    viewModel: ArtistViewModel = hiltViewModel()
) {
    Timber.i("Artist")
    val uiStateArtist = viewModel.uiStateArtistScreen

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BackgroundImage()

        ArtistContent(
            state = uiStateArtist,
            getDataOfArtistById = { viewModel.getDataOfArtistById(artistId = artistId) },
            onLikeClick = { viewModel.clickHeart(artistId = artistId) },
        )
    }
}

@ExperimentalCoilApi
@Composable
private fun ArtistContent(
    state: CommonResponse<ArtistResponse>,
    getDataOfArtistById: () -> Unit,
    onLikeClick: () -> Unit,
) {
    Timber.i("ArtistContent")

    when (state) {
        is CommonResponse.Success -> {
            Timber.i("ArtistContent.Success")
            SuccessArtist(state, onLikeClick)
        }
        is CommonResponse.Error -> {
            Timber.i("ArtistContent.Error")
            ErrorArtist()
        }
        is CommonResponse.Initial -> {
            Timber.i("ArtistContent.Initial")
            InitialArtist(getDataOfArtistById)
        }
        is CommonResponse.Load -> {
            Timber.i("ArtistContent.Load")
            LoadingArtist()
        }
    }
}

@Composable
private fun InitialArtist(getDataOfArtistById: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = StreetMusicTheme.colors.white)
        Text(
            text = stringResource(R.string.please_wait_artist),
            color = StreetMusicTheme.colors.white,
            style = StreetMusicTheme.typography.errorPermission
        )
        getDataOfArtistById()
    }
}

@ExperimentalCoilApi
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
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = StreetMusicTheme.colors.white)
        Text(
            text = stringResource(R.string.please_wait_artist),
            color = StreetMusicTheme.colors.white,
            style = StreetMusicTheme.typography.errorPermission
        )
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
        Text(
            text = stringResource(R.string.error),
            color = StreetMusicTheme.colors.white
        )
    }
}

@ExperimentalCoilApi
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