package com.example.streetmusic.ui.permissions.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.streetmusic.R
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@ExperimentalPermissionsApi
@Composable
fun LocalizeMeButton(mapPermissionState: PermissionState) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = StreetMusicTheme.dimensions.buttonsVerticalPadding),
        onClick = { mapPermissionState.launchPermissionRequest() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = StreetMusicTheme.colors.white,
            contentColor = StreetMusicTheme.colors.grayDark
        ),
        shape = StreetMusicTheme.shapes.medium
    ) {
        Text(
            text = stringResource(R.string.localize_me_button_label),
            color = StreetMusicTheme.colors.grayDark,
            style = StreetMusicTheme.typography.buttonText
        )
    }
}