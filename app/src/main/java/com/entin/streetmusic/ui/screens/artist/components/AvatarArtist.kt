package com.entin.streetmusic.ui.screens.artist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.entin.streetmusic.core.theme.StreetMusicTheme

@ExperimentalCoilApi
@Composable
fun AvatarArtist(
    artistAvatarUrl: String,
    stateLike: Boolean,
    onLikeClick: () -> Unit,
) {
    Box(contentAlignment = Alignment.BottomEnd) {
        Avatar(artistAvatarUrl)
        AvatarLike(stateLike = stateLike, onLikeClick = onLikeClick)
    }
}

@coil.annotation.ExperimentalCoilApi
@Composable
private fun Avatar(artistAvatar: String) {
    Image(
        painter = rememberImagePainter(
            data = artistAvatar,
            builder = { transformations(CircleCropTransformation()) }
        ),
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .size(StreetMusicTheme.dimensions.avatarSize)
    )
}

/**
 * Like icon
 */
@Composable
private fun AvatarLike(stateLike: Boolean, onLikeClick: () -> Unit) {
    var isFavourite: Boolean by remember { mutableStateOf(stateLike) }

    Box(
        modifier = Modifier
            .size(StreetMusicTheme.dimensions.likeSizeCircle)
            .clip(CircleShape)
            .background(StreetMusicTheme.colors.white)
            .clickable {
                onLikeClick()
                isFavourite = isFavourite.not()
            },
        contentAlignment = Alignment.Center
    ) {
        IconFavourite(isFavouriteIcon = isFavourite)
    }
}