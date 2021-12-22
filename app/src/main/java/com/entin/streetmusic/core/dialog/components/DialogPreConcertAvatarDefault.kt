package com.entin.streetmusic.core.dialog.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.core.theme.StreetMusicTheme

/**
 * Calling Dialog Window with type Permissions
 * WITHOUT content @composable
 */
@Composable
fun DialogPreConcertAvatarDefault(iconResource: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier.size(StreetMusicTheme.dimensions.avatarSize),
            painter = painterResource(iconResource),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(all = StreetMusicTheme.dimensions.allHorizontalPadding),
            text = stringResource(id = R.string.dialog_pre_concert_avatar),
            style = StreetMusicTheme.typography.dialogContent,
        )
    }
}