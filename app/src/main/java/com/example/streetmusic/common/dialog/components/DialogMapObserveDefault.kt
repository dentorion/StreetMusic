package com.example.streetmusic.common.dialog.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.streetmusic.common.model.domain.ConcertDomain
import com.example.streetmusic.ui.cityconcerts.components.MapIcon

/**
 * Calling Dialog Window with type MapObserve
 * WITHOUT content @composable
 */

@Composable
fun DialogMapObserveDefault(iconResource: Int) {
    Column {
        Text(
            modifier = Modifier.padding(all = 15.dp),
            text = "Choose a musician and follow star band!",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
        )
        Image(
            painter = painterResource(iconResource),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.padding(all = 48.dp)
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
                    .padding(vertical = 16.dp)
                    .clickable { navToMusicianPage(concert.artistId) }
                    .fillMaxWidth()
                    .height(61.dp)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                /**
                 * Title Artist Band name
                 */
                Box(
                    modifier = Modifier
                        .weight(5f)
                        .padding(start = 5.dp)
                ) {
                    Row {
                        Text(
                            text = concert.bandName,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                /**
                 * Map icon
                 */
                Box(
                    modifier = Modifier.weight(1.0f),
                    contentAlignment = Alignment.Center
                ) {
                    MapIcon(
                        latitude = concert.latitude,
                        longitude = concert.longitude,
                    )
                }
            }

        })
    }
}