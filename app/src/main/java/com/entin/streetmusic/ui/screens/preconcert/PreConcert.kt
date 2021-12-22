package com.entin.streetmusic.ui.screens.preconcert

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.entin.streetmusic.core.dialog.DialogWindow
import com.entin.streetmusic.core.model.dialog.DialogType
import com.entin.streetmusic.core.model.dialog.PreConcertAvatarDialog
import com.entin.streetmusic.core.model.dialog.PreConcertFieldDialog
import com.entin.streetmusic.core.model.response.SMResponse
import com.entin.streetmusic.ui.screens.preconcert.states.ErrorPreConcert
import com.entin.streetmusic.ui.screens.preconcert.states.InitialPreConcert
import com.entin.streetmusic.ui.screens.preconcert.states.LoadPreConcert
import com.entin.streetmusic.ui.screens.preconcert.states.SuccessPreConcert
import com.entin.streetmusic.ui.screens.start.components.BackgroundImage
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
    val dialogType = remember { mutableStateOf<DialogType>(PreConcertFieldDialog()) }

    LaunchedEffect(true) {
        viewModel.alertState.collect {
            when (it) {
                PreConcertViewModel.AlertDialog.AvatarError -> {
                    alertWindow.value = true
                    dialogType.value = PreConcertAvatarDialog()
                }
                PreConcertViewModel.AlertDialog.FieldError -> {
                    alertWindow.value = true
                    dialogType.value = PreConcertFieldDialog()
                }
            }
        }
    }

    // Dialog
    ShowAlertPreConcert(alertWindow, dialogType)
}

@Composable
private fun ShowAlertPreConcert(
    alertWindow: MutableState<Boolean>,
    dialogType: MutableState<DialogType>
) {
    if (alertWindow.value) {
        DialogWindow(
            dialogType = dialogType.value,
            openDialogState = alertWindow,
        )
    }
}

@InternalCoroutinesApi
@ExperimentalCoilApi
@Composable
private fun PreConcertContent(
    uiState: SMResponse<String>,
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
            is SMResponse.ErrorResponse -> ErrorPreConcert(uiState.SMError)
            is SMResponse.InitialResponse -> InitialPreConcert(
                viewModel = viewModel,
                navToMain = navToMain,
                uploadAvatar = { viewModel.uploadNewAvatar(it, artistId) },
            )
            is SMResponse.LoadResponse -> LoadPreConcert()
            is SMResponse.SuccessResponse -> SuccessPreConcert(navToConcert, artistId, uiState)
        }
    }
}

