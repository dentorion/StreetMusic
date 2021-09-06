package com.example.streetmusic2.ui.artist.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun StatusOnline(artistOnline: Boolean) {
    if (artistOnline) {
        IconCircle(Color.Green)
    } else {
        IconCircle(Color.Red)
    }
}