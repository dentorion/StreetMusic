package com.entin.streetmusic.ui.preconcert.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.entin.streetmusic.common.theme.StreetMusicTheme

@Composable
fun BandNameCityCountry(
    bandName: String = "",
    city: String = "",
    country: String = "",
) {
    Column(
        modifier = Modifier.padding(bottom = StreetMusicTheme.dimensions.allBottomPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = bandName.uppercase(),
            color = StreetMusicTheme.colors.white,
            style = StreetMusicTheme.typography.bandName
        )
        Text(
            text = "${city.uppercase()}, ${country.uppercase()}",
            color = StreetMusicTheme.colors.white,
            style = StreetMusicTheme.typography.cityCountry
        )
    }
}