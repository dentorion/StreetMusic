package com.example.streetmusic2.ui.artist.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun NameCityCountry(
    artistName: String,
    artistCity: String,
    artistCountry: String
) {
    Text(
        text = artistName.uppercase(),
        color = Color.White,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = "${artistCity.uppercase()} , ${artistCountry.uppercase()}",
        color = Color.White
    )
}