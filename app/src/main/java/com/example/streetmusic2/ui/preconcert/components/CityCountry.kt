package com.example.streetmusic2.ui.preconcert.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.streetmusic2.util.userpref.LocalUserPref

@Composable
fun CityCountry() {
    val bandName = LocalUserPref.current.getNameGroup()
    val userCity = LocalUserPref.current.getCity()
    val userCountry = LocalUserPref.current.getCountry()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = bandName.uppercase(),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${userCity.uppercase()}, ${userCountry.uppercase()}",
            color = Color.White
        )
    }
}