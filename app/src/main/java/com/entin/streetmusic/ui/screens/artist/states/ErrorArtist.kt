package com.entin.streetmusic.ui.screens.artist.states

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.entin.streetmusic.ui.common.ErrorMessageText
import com.entin.streetmusic.core.model.response.SMError
import com.entin.streetmusic.core.theme.StreetMusicTheme

@Composable
fun ErrorArtist(smError: SMError) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StreetMusicTheme.colors.artistGradientDown),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ErrorMessageText(smError = smError)
    }
}