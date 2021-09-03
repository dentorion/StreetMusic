package com.example.streetmusic2.ui.cityconcerts.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.streetmusic2.common.model.Concert

@Composable
fun MessageRow(
    context: Context,
    concert: Concert,
    onHeartClick: () -> Unit,
    navToMusicianPage: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .height(61.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .weight(3.0f)
            .clickable { navToMusicianPage() }
        ) {
            Text(
                text = concert.name,
                modifier = Modifier.padding(start = 12.dp),
                fontWeight = FontWeight.Bold
            )
        }

        Box(modifier = Modifier.weight(1.0f), contentAlignment = Alignment.Center) {
            Column {
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

        Box(modifier = Modifier.weight(1.0f), contentAlignment = Alignment.Center) {
            LoveIcon(actionPressed = onHeartClick, concert = concert)
        }

        Box(modifier = Modifier.weight(1.0f), contentAlignment = Alignment.Center) {
            MapIcon(context, concert)
        }
    }
}