package com.example.streetmusic2.ui.permissions.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.R

@Composable
fun LocationMusicLogo() {
    Image(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        painter = painterResource(id = R.drawable.ic_location_music),
        contentDescription = null,
    )
}