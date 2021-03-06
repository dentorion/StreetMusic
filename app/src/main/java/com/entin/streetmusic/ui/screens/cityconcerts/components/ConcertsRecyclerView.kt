package com.entin.streetmusic.ui.screens.cityconcerts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.core.model.domain.ConcertDomain
import com.entin.streetmusic.core.theme.StreetMusicTheme

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
            Image(
                painter = painterResource(id = R.drawable.ic_vocal_music),
                contentDescription = null,
                alpha = 0.5F,
            )
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