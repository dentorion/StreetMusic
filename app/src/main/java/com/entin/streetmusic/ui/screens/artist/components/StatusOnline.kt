package com.entin.streetmusic.ui.screens.artist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.entin.streetmusic.core.theme.StreetMusicTheme

@Composable
fun StatusOnline(artistOnline: Boolean) {
    Box(modifier = Modifier.padding(vertical = StreetMusicTheme.dimensions.allVerticalPadding)) {
        if (artistOnline) {
            IconCircle(StreetMusicTheme.colors.green)
        } else {
            IconCircle(StreetMusicTheme.colors.red)
        }
    }
}