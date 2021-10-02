package com.example.streetmusic2.ui.preconcert.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.R

@Composable
fun AvatarUpload() {
    Image(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape),
        painter = painterResource(id = R.drawable.ic_avatar_upload),
        contentDescription = null,
    )
}