package com.entin.streetmusic.ui.concert

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.entin.streetmusic.R
import com.entin.streetmusic.common.model.response.StreetMusicResponse
import com.entin.streetmusic.common.theme.StreetMusicTheme
import com.entin.streetmusic.ui.artist.components.StatusOnline
import com.entin.streetmusic.ui.concert.components.AutoStop
import com.entin.streetmusic.ui.concert.components.StarRating
import com.entin.streetmusic.ui.concert.components.StopConcertButton
import com.entin.streetmusic.ui.preconcert.components.Avatar
import com.entin.streetmusic.ui.preconcert.components.BandNameCityCountry
import com.entin.streetmusic.ui.start.components.BackgroundImage
import com.entin.streetmusic.util.user.LocalUserPref
import com.google.accompanist.insets.imePadding

@ExperimentalCoilApi
@Composable
fun Concert(
    viewModel: ConcertViewModel = hiltViewModel(),
    documentId: String,
    navToPreConcert: (String) -> Unit,
    userId: String
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BackgroundImage()

        when (val uiState = viewModel.uiStateConcert) {
            is StreetMusicResponse.Error -> ErrorConcert(uiState.message)
            is StreetMusicResponse.Initial -> InitialConcert(
                avatarUrl = viewModel.avatarUrl,
                stopConcert = { viewModel.stopConcert(documentId) },
            )
            is StreetMusicResponse.Load -> LoadConcert()
            is StreetMusicResponse.Success -> {
                SuccessConcert(navToPreConcert, userId)
            }
        }
    }
}

@Composable
private fun SuccessConcert(navToPreConcert: (String) -> Unit, userId: String) {
    navToPreConcert(userId)
}

@Composable
fun LoadConcert() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = StreetMusicTheme.colors.white)
        Text(
            stringResource(id = R.string.please_wait_concert),
            color = StreetMusicTheme.colors.white
        )
    }
}

@ExperimentalCoilApi
@Composable
fun InitialConcert(
    stopConcert: () -> Unit,
    avatarUrl: String,
) {
    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxWidth()
            .padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Avatar(avatarUrl)
            }
        }
        Box(Modifier.padding(StreetMusicTheme.dimensions.allHorizontalPadding)) {
            StatusOnline(true)
        }
        BandNameCityCountry(
            bandName = LocalUserPref.current.getBandName(),
            country = LocalUserPref.current.getCountry(),
            city = LocalUserPref.current.getCity(),
        )
        StarRating()
        StopConcertButton(onClick = stopConcert)
        AutoStop()
    }
}

@Composable
fun ErrorConcert(message: String) {
    DisableSelection {
        Text(
            text = message,
            color = StreetMusicTheme.colors.white,
            style = StreetMusicTheme.typography.errorPermission,
        )
    }
}