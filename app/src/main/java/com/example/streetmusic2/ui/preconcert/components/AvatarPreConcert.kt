@file:JvmName("AvatarKt")

package com.example.streetmusic2.ui.preconcert.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation

@Composable
fun AvatarPreConcert(artistAvatarUrl: String) {
    Box {
        Avatar(artistAvatarUrl)
        AvatarUpload()
    }
}

@Composable
fun Avatar(artistAvatarUrl: String) {
    Image(
        painter = rememberImagePainter(
            data = artistAvatarUrl,
            builder = { transformations(CircleCropTransformation()) }
        ),
        contentDescription = null,
        modifier = Modifier
            .size(128.dp)
            .clip(CircleShape)
            .background(Color.White)
    )
}