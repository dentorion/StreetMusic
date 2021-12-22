package com.entin.streetmusic.ui.screens.artist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.entin.streetmusic.core.theme.StreetMusicTheme

@Composable
fun IconCircle(
    color: Color,
) {
    Box(
        modifier = Modifier
            .size(StreetMusicTheme.dimensions.sizeStatusCircle)
            .clip(CircleShape)
            .background(color = color)
    )
}