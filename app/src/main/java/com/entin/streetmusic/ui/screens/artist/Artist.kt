package com.entin.streetmusic.ui.screens.artist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.entin.streetmusic.core.model.response.SMResponse
import com.entin.streetmusic.ui.screens.artist.states.ErrorArtist
import com.entin.streetmusic.ui.screens.artist.states.InitialArtist
import com.entin.streetmusic.ui.screens.artist.states.LoadingArtist
import com.entin.streetmusic.ui.screens.artist.states.SuccessArtist
import com.entin.streetmusic.ui.screens.start.components.BackgroundImage
import timber.log.Timber

@ExperimentalCoilApi
@Composable
fun Artist(
    artistId: String,
    viewModel: ArtistViewModel = hiltViewModel()
) {
    Timber.i("Artist")
    val uiStateArtist = viewModel.uiStateArtist

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
    state: SMResponse<ArtistResponse>,
    getDataOfArtistById: () -> Unit,
    onLikeClick: () -> Unit,
) {
    Timber.i("ArtistContent")

    when (state) {
        is SMResponse.SuccessResponse -> {
            Timber.i("ArtistContent.Success")
            SuccessArtist(state, onLikeClick)
        }
        is SMResponse.ErrorResponse -> {
            Timber.i("ArtistContent.Error")
            ErrorArtist(state.SMError)
        }
        is SMResponse.InitialResponse -> {
            Timber.i("ArtistContent.Initial")
            InitialArtist(getDataOfArtistById)
        }
        is SMResponse.LoadResponse -> {
            Timber.i("ArtistContent.Load")
            LoadingArtist()
        }
    }
}