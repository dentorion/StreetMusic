package com.example.streetmusic2.ui.permissions.components

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.streetmusic2.R
import com.example.streetmusic2.common.model.Concert
import com.example.streetmusic2.ui.cityconcerts.components.ConcertRow

@Composable
fun ConcertsRecyclerView(
    data: List<Concert>,
    context: Context,
    onHeartClick: (Int) -> Unit,
    navToMusicianPage: (Int) -> Unit,
) {
    if (data.isEmpty()) {
        Text(
            text = stringResource(R.string.no_concerts_in_city),
            fontSize = 20.sp,
            maxLines = 1,
            modifier = Modifier.padding(bottom = 72.dp)
        )
    } else {
        LazyColumn {
            items(data) { concert ->
                ConcertRow(
                    context = context,
                    concert = concert,
                    onHeartClick = { onHeartClick(concert.artistId) },
                    navToMusicianPage = { navToMusicianPage(concert.artistId) }
                )
            }
        }
    }
}