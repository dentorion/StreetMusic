package com.example.streetmusic2.ui.artist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun BandNameCityCountry(
    artistName: String,
    artistCity: String,
    artistCountry: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = artistName.uppercase(),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${artistCity.uppercase()}, ${artistCountry.uppercase()}",
            color = Color.White,
            fontSize = 12.sp
        )
    }
}