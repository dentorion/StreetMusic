package com.example.streetmusic2.ui.artist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.common.model.domain.ConcertDomain
import com.example.streetmusic2.ui.cityconcerts.components.MapIcon
import com.example.streetmusic2.ui.cityconcerts.components.MusicStyleIcon

@Composable
fun ConcertItem(concertDomain: ConcertDomain) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(Color.Blue.copy(alpha = 0.05f)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                MusicStyleIcon(musicType = concertDomain.styleMusic)
            }
            Text(
                text = concertDomain.bandName,
                color = Color.White,
                modifier = Modifier.padding(start = 15.dp)
            )
        }
        MapIcon(context = LocalContext.current, concertDomain = concertDomain)
    }
}