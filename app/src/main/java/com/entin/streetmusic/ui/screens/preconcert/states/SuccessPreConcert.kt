package com.entin.streetmusic.ui.screens.preconcert.states

import androidx.compose.runtime.Composable
import com.entin.streetmusic.core.model.response.SMResponse

@Composable
fun SuccessPreConcert(
    navToConcert: (String, String) -> Unit,
    artistId: String,
    uiState: SMResponse.SuccessResponse<String>
) {
    navToConcert(artistId, uiState.data)
}