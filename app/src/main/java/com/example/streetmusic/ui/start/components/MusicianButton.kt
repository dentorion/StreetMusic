package com.example.streetmusic.ui.start.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.streetmusic.R
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.example.streetmusic.util.user.LocalUserPref

@Composable
fun MusicianButton(navToPermissions: () -> Unit) {
    val userPref = LocalUserPref.current
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = StreetMusicTheme.dimensions.buttonsVerticalPadding),
        onClick = {
            userPref.setIsMusician(true)
            navToPermissions()
        },
        shape = StreetMusicTheme.shapes.medium
    ) {
        Text(
            text = stringResource(id = R.string.im_artist_button_label),
            color = StreetMusicTheme.colors.white,
            style = StreetMusicTheme.typography.buttonText
        )
    }
}
