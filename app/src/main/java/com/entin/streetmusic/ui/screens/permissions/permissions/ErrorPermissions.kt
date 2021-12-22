package com.entin.streetmusic.ui.screens.permissions.permissions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.entin.streetmusic.R
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.ui.screens.permissions.extension.openSettings

@Composable
fun ErrorPermissions() {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 15.dp), Arrangement.Center, Alignment.CenterHorizontally)
    {
        DisableSelection {
            Text(
                text = stringResource(R.string.error_location),
                color = StreetMusicTheme.colors.white,
                style = StreetMusicTheme.typography.errorPermission,
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = StreetMusicTheme.dimensions.buttonsVerticalPadding),
            onClick = { context.openSettings() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = StreetMusicTheme.colors.white,
                contentColor = StreetMusicTheme.colors.grayDark
            ),
            shape = StreetMusicTheme.shapes.medium
        ) {
            Text(
                text = stringResource(id = R.string.need_permission_button),
                style = StreetMusicTheme.typography.buttonText,
            )
        }
    }
}