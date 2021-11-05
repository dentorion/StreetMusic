package com.entin.streetmusic.ui.preconcert.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.entin.streetmusic.R
import com.entin.streetmusic.common.theme.StreetMusicTheme

@Composable
fun StarButton(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = {

            },
        ) {
            Image(
                modifier = Modifier.size(StreetMusicTheme.dimensions.starSizePre),
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
            )
        }
    }
}