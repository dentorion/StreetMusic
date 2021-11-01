package com.example.streetmusic.ui.cityconcerts.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.streetmusic.common.model.domain.ConcertDomain
import com.example.streetmusic.common.theme.StreetMusicTheme

@Composable
fun ConcertRow(
    concertDomain: ConcertDomain,
    navToMusicianPage: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(vertical = StreetMusicTheme.dimensions.concertRowVerticalPadding)
            .fillMaxWidth()
            .clickable { navToMusicianPage() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        /**
         * Style music of concert icon
         */
        MusicStyleIcon(
            modifier = Modifier.Companion
                .weight(1f)
                .size(StreetMusicTheme.dimensions.concertRowImageSize),
            musicStyle = concertDomain.styleMusic
        )
        /**
         * Title Artist Band name
         */
        TitleAndDate(
            modifier = Modifier.Companion.weight(6f),
            bandName = concertDomain.bandName,
            concertCreate = concertDomain.create,
        )
        /**
         * Map icon
         */
        MapIcon(
            modifier = Modifier
                .weight(1f)
                .size(StreetMusicTheme.dimensions.concertRowImageSize),
            latitude = concertDomain.latitude,
            longitude = concertDomain.longitude,
        )
    }
}