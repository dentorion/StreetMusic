package com.entin.streetmusic.ui.artist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.entin.streetmusic.common.theme.StreetMusicTheme

@Composable
fun BandNameCityCountry(
    artistName: String,
    artistCity: String,
    artistCountry: String
) {
    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = artistName.uppercase(),
            color = StreetMusicTheme.colors.white,
            style = StreetMusicTheme.typography.bandName
        )
        Text(
            text = "${artistCity.uppercase()}, ${artistCountry.uppercase()}",
            color = StreetMusicTheme.colors.white,
            style = StreetMusicTheme.typography.cityCountry
        )
    }
}