package com.entin.streetmusic.ui.permissions.components.states

import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.common.theme.StreetMusicTheme

@Composable
fun ErrorPermissions() {
    DisableSelection {
        Text(
            text = stringResource(R.string.error_location),
            color = StreetMusicTheme.colors.white,
            style = StreetMusicTheme.typography.errorPermission,
        )
    }
}