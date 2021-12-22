package com.entin.streetmusic.ui.screens.artist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.entin.streetmusic.core.theme.StreetMusicTheme

@Composable
fun HeaderOfList(
    online: Boolean,
    caption: String
) {
    val color = if (online) {
        StreetMusicTheme.colors.green
    } else {
        StreetMusicTheme.colors.red
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = StreetMusicTheme.dimensions.statusPlayingHorizontalPadding)
            .padding(vertical = StreetMusicTheme.dimensions.statusPlayingVerticalPadding),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconCircle(color)
        Text(
            modifier = Modifier
                .padding(horizontal = StreetMusicTheme.dimensions.statusPlayingHorizontalPadding),
            text = caption,
            color = StreetMusicTheme.colors.white,
            style = StreetMusicTheme.typography.onlineOffline
        )
    }
}