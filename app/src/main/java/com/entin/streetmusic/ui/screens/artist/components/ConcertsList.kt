package com.entin.streetmusic.ui.screens.artist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.entin.streetmusic.core.model.domain.ConcertDomain
import com.entin.streetmusic.core.theme.StreetMusicTheme

@Composable
fun ConcertsList(
    artistActiveConcertDomain: List<ConcertDomain>,
    artistExpiredConcertDomain: List<ConcertDomain>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        StreetMusicTheme.colors.artistGradientUp,
                        StreetMusicTheme.colors.artistGradientDown
                    )
                )
            )
            .padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding),
        contentAlignment = Alignment.BottomCenter
    ) {
        ConcertsRecyclerView(artistActiveConcertDomain, artistExpiredConcertDomain)
    }
}