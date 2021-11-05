package com.entin.streetmusic.ui.start.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.common.theme.StreetMusicTheme
import com.entin.streetmusic.util.user.LocalUserPref

@Composable
fun FindMusicButton(navToPermissions: () -> Unit) {
    val userPref = LocalUserPref.current
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = StreetMusicTheme.dimensions.buttonsVerticalPadding),
        onClick = {
            userPref.setIsMusician(false)
            navToPermissions()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = StreetMusicTheme.colors.white,
            contentColor = StreetMusicTheme.colors.grayDark
        ),
        shape = StreetMusicTheme.shapes.medium
    ) {
        Text(
            text = stringResource(id = R.string.find_music_button_label),
            style = StreetMusicTheme.typography.buttonText,
        )
    }
}
