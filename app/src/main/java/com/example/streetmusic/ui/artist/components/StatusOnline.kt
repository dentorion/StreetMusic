package com.example.streetmusic.ui.artist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.streetmusic.common.theme.StreetMusicTheme

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