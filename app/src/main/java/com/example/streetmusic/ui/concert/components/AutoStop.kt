package com.example.streetmusic.ui.concert.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.streetmusic.R
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.example.streetmusic.util.time.LocalTimeUtil
import com.example.streetmusic.util.user.LocalUserPref

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