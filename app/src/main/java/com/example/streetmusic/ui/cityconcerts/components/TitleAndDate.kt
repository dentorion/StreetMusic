package com.example.streetmusic.ui.cityconcerts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.example.streetmusic.util.time.LocalTimeUtil
import com.example.streetmusic.util.time.TimeUtil
import java.util.*

@Composable
fun TitleAndDate(
    modifier: Modifier,
    bandName: String,
    concertCreate: Date,
    timeUtil: TimeUtil = LocalTimeUtil.current,
) {
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement
                .spacedBy(StreetMusicTheme.dimensions.concertRowSpaceBetweenInColumn)
        ) {
            Text(
                text = bandName,
                color = StreetMusicTheme.colors.grayDark,
                style = StreetMusicTheme.typography.bandName
            )
            /**
             * Create concert date
             */
            Text(
                text = timeUtil.getStartDateString(concertCreate.time),
                color = StreetMusicTheme.colors.gray,
                style = StreetMusicTheme.typography.dateCreateConcert,
            )
        }
    }
}