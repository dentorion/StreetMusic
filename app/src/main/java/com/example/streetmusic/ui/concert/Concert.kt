package com.example.streetmusic.ui.concert

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streetmusic.R
import com.example.streetmusic.common.model.vmstate.CommonResponse
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.example.streetmusic.ui.artist.components.StatusOnline
import com.example.streetmusic.ui.concert.components.AutoStop
import com.example.streetmusic.ui.concert.components.StarRating
import com.example.streetmusic.ui.concert.components.StopConcertButton
import com.example.streetmusic.ui.preconcert.components.Avatar
import com.example.streetmusic.ui.preconcert.components.BandNameCityCountry
import com.example.streetmusic.ui.start.components.BackgroundImage
import com.example.streetmusic.util.user.LocalUserPref
import com.google.accompanist.insets.imePadding

@Composable
fun Concert(
    viewModel: ConcertViewModel = hiltViewModel(),
    documentId: String,
    navToPreConcert: (String) -> Unit,
    userId: String
) {

    Log.i("MyMusic", "documentId: $documentId")
    Log.i("MyMusic", "userId: $userId")

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BackgroundImage()

        when (val uiState = viewModel.stateConcert) {
            is CommonResponse.Error -> ErrorConcert(uiState.message)
            is CommonResponse.Initial -> InitialConcert(
                avatarUrl = viewModel.avatarUrl,
                stopConcert = { viewModel.stopConcert(documentId) },
            )
            is CommonResponse.Load -> LoadConcert()
            is CommonResponse.Success -> {
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
    Log.i("MyMusic", "9.PreConcert.Error")
    DisableSelection {
        Text(
            text = message,
            color = StreetMusicTheme.colors.white,
            style = StreetMusicTheme.typography.errorPermission,
        )
    }
}