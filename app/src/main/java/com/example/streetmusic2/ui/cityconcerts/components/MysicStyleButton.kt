package com.example.streetmusic2.ui.cityconcerts.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.common.model.music.MusicType

@Composable
fun MusicStyleIcon(musicType: MusicType) {
    Log.i("MyMusic", "Music Style Button: ${musicType.icon}")
    if(musicType !is MusicType.None) {
        Image(
            modifier = Modifier.size(27.dp),
            painter = painterResource(id = musicType.icon),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
    }

}