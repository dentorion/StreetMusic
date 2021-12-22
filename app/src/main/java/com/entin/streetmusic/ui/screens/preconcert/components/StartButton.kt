package com.entin.streetmusic.ui.screens.preconcert.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.ui.screens.preconcert.PreConcertViewModel

@Composable
fun StartButton(
    viewModel: PreConcertViewModel,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = StreetMusicTheme.dimensions.allVerticalPadding),
        onClick = {
            /**
             * Not Composable function
             * Yes, of-course, it's in onClick method =)
             */
            startConcert(viewModel = viewModel)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = StreetMusicTheme.colors.white,
            contentColor = StreetMusicTheme.colors.grayDark
        ),
        shape = StreetMusicTheme.shapes.medium
    ) {
        Text(
            text = stringResource(id = R.string.start_concert_button_label),
            style = StreetMusicTheme.typography.buttonText,
            color = StreetMusicTheme.colors.mainFirst,
        )
    }
}

/**
 * This should be done to run concert
 */
private fun startConcert(
    viewModel: PreConcertViewModel,
) {
    viewModel.runConcert()
}