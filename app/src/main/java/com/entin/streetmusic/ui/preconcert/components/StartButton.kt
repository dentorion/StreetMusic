package com.entin.streetmusic.ui.preconcert.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.ui.preconcert.PreConcertViewModel
import com.entin.streetmusic.common.theme.StreetMusicTheme
import com.entin.streetmusic.util.time.LocalTimeUtil
import com.entin.streetmusic.util.time.TimeUtil
import com.entin.streetmusic.util.user.LocalUserPref
import com.entin.streetmusic.util.user.UserSession

@Composable
fun StartButton(
    viewModel: PreConcertViewModel,
    artisId: String,
    timeUtil: TimeUtil = LocalTimeUtil.current
) {
    val userPref = LocalUserPref.current

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = StreetMusicTheme.dimensions.allVerticalPadding),
        onClick = {
            userPref.setId(artisId)
            /**
             * Not Composable function
             * Yes, of-course, it's in onClick method =)
             */
            startConcert(
                viewModel = viewModel,
                userPref = userPref,
                uId = artisId,
                timeUtil = timeUtil
            )
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = StreetMusicTheme.colors.white,
            contentColor = StreetMusicTheme.colors.grayDark
        ),
        shape = StreetMusicTheme.shapes.medium
    ) {
        Text(
            text = stringResource(id = R.string.start_concert_button_label),
            style = StreetMusicTheme.typography.buttonText,
            color = StreetMusicTheme.colors.mainFirst,
        )
    }
}

/**
 * This should be done to run concert
 */
private fun startConcert(
    viewModel: PreConcertViewModel,
    userPref: UserSession,
    uId: String,
    timeUtil: TimeUtil
) {
    viewModel.runConcert(
        latitude = userPref.getLatitude(),
        longitude = userPref.getLongitude(),
        country = userPref.getCountry(),
        city = userPref.getCity(),
        artistId = uId,
    )
    userPref.setBandName(viewModel.bandName)
    userPref.setTimeStop(timeUtil.getUnixNowPlusHour())
}