package com.example.streetmusic.ui.cityconcerts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.streetmusic.R
import com.example.streetmusic.common.model.domain.ConcertDomain
import com.example.streetmusic.common.theme.StreetMusicTheme

@Composable
fun ConcertsRecyclerView(
    data: List<ConcertDomain>,
    navToArtistPage: (String) -> Unit,
) {
    if (data.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            // TODO: set image
            Text(
                text = stringResource(R.string.no_concerts_in_city),
                color = StreetMusicTheme.colors.mainFirst,
                style = StreetMusicTheme.typography.errorPermission
            )
        }
    } else {
        LazyColumn {
            items(data) { concert ->
                ConcertRow(
                    concertDomain = concert,
                    navToMusicianPage = { navToArtistPage(concert.artistId) }
                )
            }
        }
    }
}