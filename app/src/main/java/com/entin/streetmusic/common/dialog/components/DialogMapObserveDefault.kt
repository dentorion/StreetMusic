package com.entin.streetmusic.common.dialog.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.common.model.domain.ConcertDomain
import com.entin.streetmusic.common.theme.StreetMusicTheme
import com.entin.streetmusic.ui.cityconcerts.components.MapIcon
import com.entin.streetmusic.ui.cityconcerts.components.MusicStyleIcon
import com.entin.streetmusic.ui.cityconcerts.components.TitleAndDate

/**
 * Calling Dialog Window with type MapObserve
 * WITHOUT content @composable
 */
@Composable
fun DialogMapObserveDefault(iconResource: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.padding(all = StreetMusicTheme.dimensions.allHorizontalPadding),
            text = stringResource(id = R.string.dialog_map),
            style = StreetMusicTheme.typography.dialogContent,
        )
        Image(
            modifier = Modifier.size(StreetMusicTheme.dimensions.avatarSize),
            painter = painterResource(iconResource),
            contentDescription = null,
        )
    }
}

/**
 * Calling Dialog with type MapObserve
 * WITH content @composable
 */
@Composable
fun DialogMapObserve(
    listArtist: List<ConcertDomain>,
    navToMusicianPage: (String) -> Unit,
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
                    musicStyle = concert.styleMusic
                )
                /**
                 * Title Artist Band name
                 */
                TitleAndDate(
                    modifier = Modifier.weight(5f),
                    bandName = concert.bandName,
                    concertCreate = concert.create,
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