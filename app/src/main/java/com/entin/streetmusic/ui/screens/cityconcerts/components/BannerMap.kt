package com.entin.streetmusic.ui.screens.cityconcerts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.entin.streetmusic.R
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun BannerMap(navToMapObserve: () -> Unit) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .background(StreetMusicTheme.colors.transparent)
                .clickable {
                    navToMapObserve()
                },
            painter = painterResource(id = R.drawable.ic_map_observe),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}