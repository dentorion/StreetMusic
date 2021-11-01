package com.example.streetmusic.ui.preconcert

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streetmusic.R
import com.example.streetmusic.common.model.vmstate.CommonResponse
import com.example.streetmusic.ui.artist.components.StatusOnline
import com.example.streetmusic.ui.preconcert.components.*
import com.example.streetmusic.ui.start.components.BackgroundImage
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.example.streetmusic.util.time.LocalTimeUtil
import com.example.streetmusic.util.user.LocalUserPref
import com.google.accompanist.insets.imePadding

@Composable
fun PreConcert(
    viewModel: PreConcertViewModel = hiltViewModel(),
    artistId: String,
    navToConcert: (String, String) -> Unit,
    navToMain: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {
        BackgroundImage()

        Log.i("MyMusic", "9.PreConcert")
        val uiState = viewModel.statePreConcert

        when (uiState) {
            is CommonResponse.Error -> ErrorPreConcert(uiState.message)
            is CommonResponse.Initial -> InitialPreConcert(
                viewModel = viewModel,
                artisId = artistId,
                navToMain = navToMain,
                uploadAvatar = { viewModel.avatarUploadAndSaveDb(it, artistId) },
            )
            is CommonResponse.Load -> LoadPreConcert()
            is CommonResponse.Success -> SuccessPreConcert(navToConcert, artistId, uiState)
        }
    }
}

@Composable
private fun SuccessPreConcert(
    navToConcert: (String, String) -> Unit,
    artistId: String,
    uiState: CommonResponse.Success<String>
) {
    navToConcert(artistId, uiState.data)
}

@Composable
private fun InitialPreConcert(
    viewModel: PreConcertViewModel,
    artisId: String,
    navToMain: () -> Unit,
    uploadAvatar: (Uri) -> Unit,
) {
    val scrollState = rememberScrollState()
    val timeUtil = LocalTimeUtil.current
    val userPref = LocalUserPref.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = StreetMusicTheme.dimensions.horizontalPaddingPre)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            /**
             * Star icon
             */
            StarButton(modifier = Modifier.weight(1f))
            /**
             * Avatar
             */
            AvatarPreConcert(
                modifier = Modifier.weight(2f),
                viewModel = viewModel,
                uploadAvatar = uploadAvatar,
            )
            /**
             * Exit icon
             */
            ExitButton(
                modifier = Modifier.weight(1f),
                navToMain = navToMain
            )
        }

        /**
         * ON_Line / Off-line status
         */
        StatusOnline(false)

        /**
         * City and Country
         */
        BandNameCityCountry(
            bandName = viewModel.bandName,
            city = userPref.getCity(),
            country = userPref.getCountry()
        )

        /**
         * Style Music buttons selector
         */
        StyleMusicButtons(
            onClick = {
                userPref.setStyleMusic(it.styleName)
                viewModel.musicType = it
            },
            actualChoice = viewModel.musicType,
        )

        /**
         * Text fields: bandName, address, description
         */
        TextFields(viewModel, userPref)

        /**
         * Start Concert button
         */
        StartButton(viewModel, artisId, timeUtil)
    }
}

@Composable
private fun LoadPreConcert() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = StreetMusicTheme.colors.white)
        Text(
            text = stringResource(id = R.string.please_wait_pre),
            color = StreetMusicTheme.colors.white
        )
    }
}

@Composable
private fun ErrorPreConcert(message: String) {
    Log.i("MyMusic", "9.PreConcert.Error")
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisableSelection {
            Text(
                text = message,
                color = StreetMusicTheme.colors.mainFirst,
            )
        }
    }
}

