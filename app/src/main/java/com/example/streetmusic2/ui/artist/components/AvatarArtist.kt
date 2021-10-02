package com.example.streetmusic2.ui.artist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation

@Composable
fun Avatar(artistAvatarUrl: String = "https://image.shutterstock.com/image-illustration/casual-young-black-girl-purple-260nw-1806646354.jpg") {
    Image(
        painter = rememberImagePainter(
            data = artistAvatarUrl,
            builder = { transformations(CircleCropTransformation()) }
        ),
        contentDescription = null,
        modifier = Modifier
            .size(128.dp)
            .clip(CircleShape)
            .background(Color.Gray)
    )
}