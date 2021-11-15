package com.entin.streetmusic.ui.preconcert

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.entin.streetmusic.R
import com.entin.streetmusic.common.dialog.DialogWindow
import com.entin.streetmusic.common.model.dialog.DialogType
import com.entin.streetmusic.common.model.vmstate.CommonResponse
import com.entin.streetmusic.common.theme.StreetMusicTheme
import com.entin.streetmusic.ui.artist.components.StatusOnline
import com.entin.streetmusic.ui.preconcert.components.*
import com.entin.streetmusic.ui.start.components.BackgroundImage
import com.entin.streetmusic.util.user.LocalUserPref
import com.google.accompanist.insets.imePadding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@InternalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun PreConcert(
    viewModel: PreConcertViewModel = hiltViewModel(),
    artistId: String,
    navToConcert: (String, String) -> Unit,
    navToMain: () -> Unit,
) {
    // UiState
    val uiState = viewModel.uiStatePreConcert
    PreConcertContent(uiState, viewModel, artistId, navToConcert, navToMain)

    // Alert
    val alertWindow = remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.alertState.collect {
            when (it) {
                true -> alertWindow.value = true
            }
        }
    }

    // Dialog
    ShowAlertPreConcert(alertWindow)
}

@Composable
fun ShowAlertPreConcert(alertWindow: MutableState<Boolean>) {
    if (alertWindow.value) {
        DialogWindow(
            dialogType = DialogType.PreConcertError(),
            openDialogState = alertWindow,
        )
    }
}

@ExperimentalCoilApi
@Composable
private fun PreConcertContent(
    uiState: CommonResponse<String>,
    viewModel: PreConcertViewModel,
    artistId: String,
    navToConcert: (String, String) -> Unit,
    navToMain: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {
        BackgroundImage()

        Timber.i("PreConcert")

        when (uiState) {
            is CommonResponse.Error -> ErrorPreConcert()
            is CommonResponse.Initial -> InitialPreConcert(
                viewModel = viewModel,
                artistId = artistId,
                navToMain = navToMain,
                uploadAvatar = { viewModel.avatarUploadAndSaveDb(it, artistId) },
            )
            is CommonResponse.Load -> LoadPreConcert()
            is CommonResponse.Success -> SuccessPreConcert(navToConcert, artistId, uiState)
        }
    }
}

@Composable
fun ErrorPreConcert() {
    TODO("Not yet implemented")
}

@Composable
private fun SuccessPreConcert(
    navToConcert: (String, String) -> Unit,
    artistId: String,
    uiState: CommonResponse.Success<String>
) {
    navToConcert(artistId, uiState.data)
}

@ExperimentalCoilApi
@Composable
private fun InitialPreConcert(
    viewModel: PreConcertViewModel,
    artistId: String,
    navToMain: () -> Unit,
    uploadAvatar: (Uri) -> Unit,
) {
    val scrollState = rememberScrollState()
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
        StartButton(viewModel, artistId)
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

