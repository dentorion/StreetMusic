package com.example.streetmusic.ui.artist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.streetmusic.R
import com.example.streetmusic.common.model.domain.ConcertDomain
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun ConcertsRecyclerView(
    artistActiveConcertDomain: List<ConcertDomain>,
    artistExpiredConcertDomain: List<ConcertDomain>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding)
    ) {
        /**
         * On-line
         */
        item {
            HeaderOfList(online = true, caption = stringResource(id = R.string.now_playing))
        }
        if (artistActiveConcertDomain.isEmpty()) {
            item {
                NoConcerts()
            }
        } else {
            items(items = artistActiveConcertDomain) { concert ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = StreetMusicTheme.shapes.small,
                    backgroundColor = StreetMusicTheme.colors.backgroundOnline,
                    elevation = StreetMusicTheme.dimensions.elevationCard,
                    contentColor = StreetMusicTheme.colors.white,
                ) {
                    Column {
                        ConcertItem(concert)
                        DescriptionConcert(
                            concertDomain = concert,
                            color = StreetMusicTheme.colors.green
                        )
                    }
                }
            }
        }
        /**
         * Off-line
         */
        item {
            HeaderOfList(online = false, caption = stringResource(id = R.string.last_playing))
        }
        if (artistExpiredConcertDomain.isEmpty()) {
            item {
                NoConcerts()
            }
        } else {
            items(items = artistExpiredConcertDomain) { concert ->
                ConcertItem(concert)
                DescriptionConcert(concertDomain = concert, color = StreetMusicTheme.colors.red)
            }
        }
    }
}