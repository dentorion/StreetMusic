package com.example.streetmusic.ui.cityconcerts.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.streetmusic.R
import com.example.streetmusic.common.theme.StreetMusicTheme
import java.util.*

@Composable
fun SortStyleAllConcertsByCity(
    userCity: String,
    onAllClick: () -> Unit,
    actualAllStyle: Boolean,
    enabled: Boolean
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = StreetMusicTheme.dimensions.buttonsVerticalPadding)
            .padding(horizontal = StreetMusicTheme.dimensions.paddingBetweenButtons),
        border = if (actualAllStyle) {
            BorderStroke(
                width = StreetMusicTheme.dimensions.borderStrokeActiveButton,
                color = StreetMusicTheme.colors.divider
            )
        } else {
            null
        },
        onClick = {
            onAllClick()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = StreetMusicTheme.colors.white,
            contentColor = StreetMusicTheme.colors.grayDark
        ),
        shape = StreetMusicTheme.shapes.medium,
        enabled = enabled
    ) {
        Text(
            text = stringResource(id = R.string.all_concerts_label) +
                    userCity.uppercase(Locale.getDefault()),
            style = StreetMusicTheme.typography.buttonText,
        )
    }
}