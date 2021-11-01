package com.example.streetmusic.ui.artist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.streetmusic.R
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun Header(
    artistAvatar: String,
    artistOnline: Boolean,
    artistName: String,
    artistCity: String,
    artistCountry: String,
    isFavouriteArtist: Boolean,
    onLikeClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(vertical = StreetMusicTheme.dimensions.allVerticalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AvatarArtist(
                artistAvatarUrl = artistAvatar,
                stateLike = isFavouriteArtist,
                onLikeClick = onLikeClick,
            )

            StatusOnline(artistOnline)

            BandNameCityCountry(artistName, artistCity, artistCountry)
        }
    }
}