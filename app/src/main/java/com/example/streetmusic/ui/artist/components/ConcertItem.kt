package com.example.streetmusic.ui.artist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.streetmusic.common.model.domain.ConcertDomain
import com.example.streetmusic.ui.cityconcerts.components.MapIcon
import com.example.streetmusic.ui.cityconcerts.components.MusicStyleIcon
import com.example.streetmusic.common.theme.StreetMusicTheme

@Composable
fun ConcertItem(concertDomain: ConcertDomain) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(StreetMusicTheme.shapes.small)
            .background(StreetMusicTheme.colors.concertCardBackground),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(StreetMusicTheme.dimensions.concertRowImageSize)
                    .clip(CircleShape)
                    .background(StreetMusicTheme.colors.white)
                    .padding(StreetMusicTheme.dimensions.innerPaddingMusicStyle),
                contentAlignment = Alignment.Center
            ) {
                MusicStyleIcon(
                    musicStyle = concertDomain.styleMusic
                )
            }
            Text(
                modifier = Modifier.padding(start = StreetMusicTheme.dimensions.allHorizontalPadding),
                text = concertDomain.bandName,
                color = StreetMusicTheme.colors.white,
                style = StreetMusicTheme.typography.bandName,
            )
        }
        MapIcon(
            modifier = Modifier.size(StreetMusicTheme.dimensions.concertRowImageSize),
            latitude = concertDomain.latitude,
            longitude = concertDomain.longitude,
        )
    }
}