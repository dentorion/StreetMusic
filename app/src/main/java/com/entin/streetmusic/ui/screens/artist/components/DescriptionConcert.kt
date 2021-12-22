package com.entin.streetmusic.ui.screens.artist.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.core.model.domain.ConcertDomain
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.data.time.LocalTimeUtil

@Composable
fun DescriptionConcert(concertDomain: ConcertDomain, color: Color) {
    val timeUtil = LocalTimeUtil.current

    Row(
        modifier = Modifier
            .padding(vertical = StreetMusicTheme.dimensions.verticalPaddingConcertDetails)
            .padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding)
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
    ) {
        Divider(
            color = color, modifier = Modifier
                .fillMaxHeight()
                .width(StreetMusicTheme.dimensions.verticalDividerThickness)
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = StreetMusicTheme.dimensions.startPaddingConcertDetails)
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.started) + SPACER,
                    color = StreetMusicTheme.colors.white,
                    style = StreetMusicTheme.typography.startDateLabel,
                )
                Text(
                    text = timeUtil.getStartDateString(concertDomain.create.time) + SPACER,
                    color = StreetMusicTheme.colors.white,
                    style = StreetMusicTheme.typography.startDate,
                )
                Text(
                    text = stringResource(id = R.string.at) + SPACER +
                            timeUtil.getStartHoursString(concertDomain.create.time),
                    color = StreetMusicTheme.colors.white,
                    style = StreetMusicTheme.typography.startDate,
                )
            }

            if (concertDomain.address.isNotEmpty()) {
                Text(
                    text = concertDomain.address,
                    color = StreetMusicTheme.colors.white,
                    style = StreetMusicTheme.typography.description,
                    modifier = Modifier.padding(
                        top = StreetMusicTheme.dimensions.rowTopPaddingConcertDescription
                    )
                )
            }
            if (concertDomain.description.isNotEmpty()) {
                Text(
                    text = concertDomain.description,
                    color = StreetMusicTheme.colors.white,
                    style = StreetMusicTheme.typography.description,
                    modifier = Modifier.padding(
                        top = StreetMusicTheme.dimensions.rowTopPaddingConcertDescription
                    )
                )
            }

        }
    }
}

const val SPACER = " "