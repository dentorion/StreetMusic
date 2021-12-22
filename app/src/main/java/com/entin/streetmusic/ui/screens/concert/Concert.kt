package com.entin.streetmusic.ui.screens.concert

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.entin.streetmusic.core.model.response.SMResponse
import com.entin.streetmusic.ui.screens.concert.states.ErrorConcert
import com.entin.streetmusic.ui.screens.concert.states.InitialConcert
import com.entin.streetmusic.ui.screens.concert.states.LoadConcert
import com.entin.streetmusic.ui.screens.concert.states.SuccessConcert
import com.entin.streetmusic.ui.screens.start.components.BackgroundImage

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
            is SMResponse.ErrorResponse -> ErrorConcert(uiState.SMError)
            is SMResponse.InitialResponse -> InitialConcert(
                avatarUrl = viewModel.avatarUrl,
                stopConcert = { viewModel.stopConcert(documentId) },
            )
            is SMResponse.LoadResponse -> LoadConcert()
            is SMResponse.SuccessResponse -> {
                SuccessConcert(navToPreConcert, userId)
            }
        }
    }
}