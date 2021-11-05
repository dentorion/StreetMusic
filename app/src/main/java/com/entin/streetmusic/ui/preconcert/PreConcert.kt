package com.entin.streetmusic.ui.preconcert

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.entin.streetmusic.util.time.LocalTimeUtil
import com.entin.streetmusic.util.user.LocalUserPref
import com.google.accompanist.insets.imePadding
import timber.log.Timber

@ExperimentalCoilApi
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

        Timber.i("PreConcert")
        val uiState = viewModel.statePreConcert

        when (uiState) {
            is CommonResponse.Error -> InitialPreConcert(
                viewModel = viewModel,
                artistId = artistId,
                navToMain = navToMain,
                uploadAvatar = { viewModel.avatarUploadAndSaveDb(it, artistId) },
                errorState = true
            )
            is CommonResponse.Initial -> InitialPreConcert(
                viewModel = viewModel,
                artistId = artistId,
                navToMain = navToMain,
                uploadAvatar = { viewModel.avatarUploadAndSaveDb(it, artistId) },
                errorState = false
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

@ExperimentalCoilApi
@Composable
private fun InitialPreConcert(
    viewModel: PreConcertViewModel,
    artistId: String,
    navToMain: () -> Unit,
    uploadAvatar: (Uri) -> Unit,
    errorState: Boolean
) {
    val scrollState = rememberScrollState()
    val timeUtil = LocalTimeUtil.current
    val userPref = LocalUserPref.current
    val openDialog = remember { mutableStateOf(errorState) }
    openDialog.value = errorState

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
        StartButton(viewModel, artistId, timeUtil)

        /**
         * Dialog
         */
        if (errorState) {
            DialogWindow(
                dialogState = openDialog,
                dialogType = DialogType.PreConcertError(),
                onOkClicked = { openDialog.value = false }
            )
        }
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

