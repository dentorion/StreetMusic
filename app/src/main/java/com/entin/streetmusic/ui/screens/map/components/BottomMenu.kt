package com.entin.streetmusic.ui.screens.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.entin.streetmusic.core.model.map.ShortConcertForMap
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.ui.screens.artist.components.IconCircle
import com.google.accompanist.insets.navigationBarsPadding

/**
 * Buttons
 */
@Composable
fun BottomMenu(
    navToCityConcerts: () -> Unit,
    navToArtistPage: (String) -> Unit,
    concerts: List<ShortConcertForMap>,
    alertState: MutableState<Boolean>,
) {
    Box(
        modifier = Modifier
            .clip(StreetMusicTheme.shapes.small)
            .fillMaxWidth()
            .background(StreetMusicTheme.colors.mainFirst)
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .padding(all = StreetMusicTheme.dimensions.allHorizontalPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(
                navToCityConcerts = navToCityConcerts,
                modifier = Modifier.weight(1f)
            )

            Box(modifier = Modifier.padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding)) {
                IconCircle(Color.Green)
            }

            ListButton(
                concerts = concerts,
                navToArtistPage = navToArtistPage,
                modifier = Modifier.weight(1f),
                alertState = alertState
            )
        }
    }
}