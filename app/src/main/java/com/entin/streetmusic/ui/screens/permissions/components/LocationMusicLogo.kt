package com.entin.streetmusic.ui.screens.permissions.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.entin.streetmusic.R

@Composable
fun LocationMusicLogo() {
    Image(
        painter = painterResource(id = R.drawable.ic_location_music),
        contentDescription = null,
    )
}