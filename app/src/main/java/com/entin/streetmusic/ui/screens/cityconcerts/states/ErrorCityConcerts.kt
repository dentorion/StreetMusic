package com.entin.streetmusic.ui.screens.cityconcerts.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.entin.streetmusic.ui.common.ErrorMessageText
import com.entin.streetmusic.core.model.response.SMError
import timber.log.Timber

@Composable
fun ErrorCityConcerts(smError: SMError) {
    Timber.i("CityConcertsContent.Error")
    Column(modifier = Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        ErrorMessageText(smError = smError)
    }
}