package com.example.streetmusic2.ui.artist.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.streetmusic2.common.model.Concert
import com.example.streetmusic2.ui.cityconcerts.components.MapIcon
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ConcertsByArtistRecyclerView(artistActiveConcert: List<Concert>) {
    LazyColumn {
        items(artistActiveConcert) { concert ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = concert.name, color = Color.White)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
                            .format(concert.timeStart),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = SimpleDateFormat("HH:mm", Locale.getDefault())
                            .format(concert.timeStart)
                                + " - " +
                                SimpleDateFormat("HH:mm", Locale.getDefault())
                                    .format(concert.timeStop),
                        color = Color.White,
                        fontSize = 11.sp
                    )
                }
                MapIcon(context = LocalContext.current, concert = concert)
            }
        }
    }
}