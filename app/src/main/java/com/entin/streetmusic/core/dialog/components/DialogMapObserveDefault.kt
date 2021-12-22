package com.entin.streetmusic.core.dialog.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.entin.streetmusic.R
import com.entin.streetmusic.core.model.map.ShortConcertForMap
import com.entin.streetmusic.core.model.music.convertToMusicType
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.ui.screens.cityconcerts.components.MapIcon
import com.entin.streetmusic.ui.screens.cityconcerts.components.MusicStyleIcon
import com.entin.streetmusic.ui.screens.cityconcerts.components.TitleAndDate
import java.util.*

/**
 * Calling Dialog Window with type MapObserve
 * WITHOUT content @composable
 */
@Composable
fun DialogMapObserveDefault() {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.padding(all = StreetMusicTheme.dimensions.allHorizontalPadding),
            text = stringResource(id = R.string.no_concerts_in_city),
            style = StreetMusicTheme.typography.dialogContent,
            textAlign = TextAlign.Start,
        )
    }
}

/**
 * Calling Dialog with type MapObserve
 * WITH content @composable
 */
@Composable
fun DialogMapObserve(
    listArtist: List<ShortConcertForMap>,
    navToMusicianPage: (String) -> Unit,
) {
    if (listArtist.isNotEmpty()) RecyclerView(listArtist, navToMusicianPage)
    else DialogMapObserveDefault()
}

@Composable
private fun RecyclerView(
    listArtist: List<ShortConcertForMap>,
    navToMusicianPage: (String) -> Unit
) {
    LazyColumn {
        items(listArtist, itemContent = { concert ->
            Row(
                modifier = Modifier
                    .padding(vertical = StreetMusicTheme.dimensions.allVerticalPadding)
                    .fillMaxWidth()
                    .clickable { navToMusicianPage(concert.artistId) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                /**
                 * Style music of concert icon
                 */
                MusicStyleIcon(
                    modifier = Modifier
                        .weight(1f)
                        .size(StreetMusicTheme.dimensions.concertRowImageSize),
                    musicStyle = convertToMusicType(concert.styleMusic)
                )
                /**
                 * Title Artist Band name
                 */
                TitleAndDate(
                    modifier = Modifier.weight(5f),
                    bandName = concert.bandName,
                    concertCreate = Date(concert.create),
                )
                /**
                 * Map icon
                 */
                MapIcon(
                    modifier = Modifier
                        .weight(1f)
                        .size(StreetMusicTheme.dimensions.concertRowImageSize),
                    latitude = concert.latitude,
                    longitude = concert.longitude,
                )
            }
        })
    }
}