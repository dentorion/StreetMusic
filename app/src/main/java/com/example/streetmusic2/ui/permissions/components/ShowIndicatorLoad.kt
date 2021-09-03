package com.example.streetmusic2.ui.permissions.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ShowIndicatorLoad(progressValue: Float) {
    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
        progress = progressValue,
        color = Color.White
    )
}