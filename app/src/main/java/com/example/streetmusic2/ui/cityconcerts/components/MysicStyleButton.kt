package com.example.streetmusic2.ui.cityconcerts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.R

@Composable
fun MusicStyleIcon(styleMusic: String) {
    when (styleMusic) {
        "rock" -> RockMusic()
        "classic" -> ClassicMusic()
        "pop" -> PopMusic()
        "dancing" -> DancingMusic()
        "vocal" -> VocalMusic()
    }
}

@Composable
private fun RockMusic() {
    Image(
        modifier = Modifier
            .width(25.dp)
            .height(25.dp),
        painter = painterResource(id = R.drawable.ic_rock_music),
        contentDescription = null,
        contentScale = ContentScale.Fit,
    )
}

@Composable
private fun ClassicMusic() {
    Image(
        modifier = Modifier
            .width(25.dp)
            .height(25.dp),
        painter = painterResource(id = R.drawable.ic_classic_music),
        contentDescription = null,
        contentScale = ContentScale.Fit,
    )
}

@Composable
private fun DancingMusic() {
    Image(
        modifier = Modifier
            .width(25.dp)
            .height(25.dp),
        painter = painterResource(id = R.drawable.ic_dancing_music),
        contentDescription = null,
        contentScale = ContentScale.Fit,
    )
}

@Composable
private fun PopMusic() {
    Image(
        modifier = Modifier
            .width(25.dp)
            .height(25.dp),
        painter = painterResource(id = R.drawable.ic_pop_music),
        contentDescription = null,
        contentScale = ContentScale.Fit,
    )
}

@Composable
private fun VocalMusic() {
    Image(
        modifier = Modifier
            .width(25.dp)
            .height(25.dp),
        painter = painterResource(id = R.drawable.ic_vocal_music),
        contentDescription = null,
        contentScale = ContentScale.Fit,
    )
}