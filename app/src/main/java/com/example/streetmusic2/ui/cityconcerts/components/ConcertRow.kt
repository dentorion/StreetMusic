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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.streetmusic2.common.model.Concert

@Composable
fun ConcertRow(
    context: Context,
    concert: Concert,
    onHeartClick: () -> Unit,
    navToMusicianPage: () -> Unit,
) {
    var isFavourite: Boolean by rememberSaveable { mutableStateOf(concert.isFavourite) }
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .clickable { navToMusicianPage() }
            .fillMaxWidth()
            .height(61.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(3.0f)) {
            Text(
                text = concert.name,
                modifier = Modifier.padding(start = 12.dp),
                fontWeight = FontWeight.Bold
            )
        }

        Box(modifier = Modifier.weight(1.0f), contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    MusicStyleIcon(concert.styleMusic)
                }
                Box {
                    Text(
                        text = "${concert.playingTimeMinutes} min",
                        fontSize = 10.sp,
                        color = Color.Gray,
                    )
                }
            }
        }

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

        Box(
            modifier = Modifier
                .weight(1.0f)
                .fillMaxHeight()
                .clip(CircleShape)
                .clickable {  },
            contentAlignment = Alignment.Center
        ) {
            MapIcon(context, concert)
        }
    }
}