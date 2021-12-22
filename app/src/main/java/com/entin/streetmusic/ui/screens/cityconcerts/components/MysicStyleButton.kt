package com.entin.streetmusic.ui.screens.cityconcerts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.entin.streetmusic.core.model.music.MusicType

@Composable
fun MusicStyleIcon(modifier: Modifier = Modifier, musicStyle: MusicType) {
    if (musicStyle !is MusicType.None) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            MusicStyleIconContent(musicStyle.icon)
        }
    }
}

@Composable
private fun MusicStyleIconContent(musicType: Int) {
    Image(
        painter = painterResource(id = musicType),
        contentDescription = null,
    )
}