package com.example.streetmusic2.ui.cityconcerts.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.common.model.concert.ConcertDomain

@Composable
fun ConcertRow(
    context: Context,
    concertDomain: ConcertDomain,
    onHeartClick: () -> Unit,
    navToMusicianPage: () -> Unit,
) {
    var isFavourite: Boolean by rememberSaveable { mutableStateOf(concertDomain.isFavourite) }
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .clickable { navToMusicianPage() }
            .fillMaxWidth()
            .height(61.dp)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        /**
         * Like icon
         */
        Box(
            modifier = Modifier
                .weight(1.0f)
                .fillMaxHeight()
                .clip(CircleShape)
                .clickable {
                    onHeartClick()
                    isFavourite = isFavourite.not()
                },
            contentAlignment = Alignment.Center
        ) {
            IconFavourite(isFavouriteIcon = isFavourite)
        }
        /**
         * Title Artist Band name
         */
        Box(modifier = Modifier.weight(5f).padding(start = 5.dp)) {
            Row {
                Text(
                    text = concertDomain.name,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        /**
         * Style music of concert
         */
        Box(
            modifier = Modifier
                .weight(1.0f)
                .fillMaxHeight()
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            MusicStyleIcon(concertDomain.styleMusic)
        }

        /**
         * Map icon
         */
        Box(
            modifier = Modifier.weight(1.0f),
            contentAlignment = Alignment.Center
        ) {
            MapIcon(context, concertDomain)
        }
    }
}