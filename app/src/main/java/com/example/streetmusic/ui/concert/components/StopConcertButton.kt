package com.example.streetmusic.ui.concert.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.streetmusic.R
import com.example.streetmusic.common.theme.StreetMusicTheme

@Composable
fun StopConcertButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .padding(vertical = StreetMusicTheme.dimensions.allVerticalPadding)
            .fillMaxWidth(),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = StreetMusicTheme.colors.white,
            contentColor = StreetMusicTheme.colors.grayDark
        ),
        shape = StreetMusicTheme.shapes.medium
    ) {
        Text(
            text = stringResource(id = R.string.stop_concert_button_label),
            style = StreetMusicTheme.typography.buttonText,
            color = StreetMusicTheme.colors.red
        )
    }
}