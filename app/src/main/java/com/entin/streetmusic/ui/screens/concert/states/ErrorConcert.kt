package com.entin.streetmusic.ui.screens.concert.states

import androidx.compose.runtime.Composable
import com.entin.streetmusic.ui.common.ErrorMessageText
import com.entin.streetmusic.core.model.response.SMError


@Composable
fun ErrorConcert(smError: SMError) {
    ErrorMessageText(smError = smError)
}