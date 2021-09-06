package com.example.streetmusic2.ui.cityconcerts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.R

@Composable
fun IconFavourite(
    isFavouriteIcon: Boolean
) {
    Image(
        modifier = Modifier.size(24.dp),
        painter = if (isFavouriteIcon) {
            painterResource(id = R.drawable.ic_like_checked)
        } else {
            painterResource(id = R.drawable.ic_like_unchecked)
        },
        contentDescription = null,
    )
}