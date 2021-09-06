package com.example.streetmusic2.ui.artist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun IconCircle(
    color: Color,
) {
    Box(modifier = Modifier.size(31.dp).clip(CircleShape)) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .size(10.dp)
                .clip(CircleShape)
                .background(color = color)
                .shadow(
                    elevation = 16.dp,
                    shape = CircleShape,
                    clip = true
                )
        )
    }

}