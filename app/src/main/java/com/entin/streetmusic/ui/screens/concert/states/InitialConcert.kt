package com.entin.streetmusic.ui.screens.concert.states

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.ui.screens.artist.components.StatusOnline
import com.entin.streetmusic.ui.screens.concert.components.AutoStop
import com.entin.streetmusic.ui.screens.concert.components.StarRating
import com.entin.streetmusic.ui.screens.concert.components.StopConcertButton
import com.entin.streetmusic.ui.screens.preconcert.components.Avatar
import com.entin.streetmusic.ui.screens.preconcert.components.BandNameCityCountry
import com.entin.streetmusic.data.user.LocalUserPref
import com.google.accompanist.insets.imePadding

@ExperimentalCoilApi
@Composable
fun InitialConcert(
    stopConcert: () -> Unit,
    avatarUrl: String,
) {
    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxWidth()
            .padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Avatar(avatarUrl)
            }
        }
        Box(Modifier.padding(StreetMusicTheme.dimensions.allHorizontalPadding)) {
            StatusOnline(true)
        }
        BandNameCityCountry(
            bandName = LocalUserPref.current.getBandName(),
            country = LocalUserPref.current.getCountry(),
            city = LocalUserPref.current.getCity(),
        )
        StarRating()
        StopConcertButton(onClick = stopConcert)
        AutoStop()
    }
}