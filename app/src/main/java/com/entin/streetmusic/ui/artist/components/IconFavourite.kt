package com.entin.streetmusic.ui.artist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.entin.streetmusic.R
import com.entin.streetmusic.common.theme.StreetMusicTheme

@Composable
fun IconFavourite(
    isFavouriteIcon: Boolean
) {
    Image(
        modifier = Modifier
            .size(StreetMusicTheme.dimensions.likeSizeHeart)
            .padding(top = StreetMusicTheme.dimensions.likeHeartTopPadding),
        painter = if (isFavouriteIcon) {
            painterResource(id = R.drawable.ic_like_checked)
        } else {
            painterResource(id = R.drawable.ic_like_unchecked)
        },
        contentDescription = null,
    )
}