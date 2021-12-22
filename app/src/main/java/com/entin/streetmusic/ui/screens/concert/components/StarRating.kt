package com.entin.streetmusic.ui.screens.concert.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.entin.streetmusic.R
import com.entin.streetmusic.core.theme.StreetMusicTheme

@Composable
fun StarRating() {
    Box(Modifier.padding(StreetMusicTheme.dimensions.allHorizontalPadding)) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Min),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(StreetMusicTheme.dimensions.starSizePre),
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = StreetMusicTheme.dimensions.dividerTopPaddingConcert),
                color = StreetMusicTheme.colors.white
            )
            Text(text = "BEGINNER", color = StreetMusicTheme.colors.white, fontSize = 12.sp)
        }
    }
}