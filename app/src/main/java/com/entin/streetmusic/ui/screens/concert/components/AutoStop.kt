package com.entin.streetmusic.ui.screens.concert.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.data.time.LocalTimeUtil
import com.entin.streetmusic.data.user.LocalUserPref

@Composable
fun AutoStop() {
    Text(
        text = stringResource(id = R.string.auto_stop) + " ${getEndTimeString()}",
        color = StreetMusicTheme.colors.white,
        style = StreetMusicTheme.typography.autoStop
    )
}

@Composable
private fun getEndTimeString(): String {
    val userPref = LocalUserPref.current
    val timeUtil = LocalTimeUtil.current
    return timeUtil.getStringConcertTime(userPref.getTimeStop())
}