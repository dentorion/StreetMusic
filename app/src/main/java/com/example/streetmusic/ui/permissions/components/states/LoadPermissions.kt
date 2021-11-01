package com.example.streetmusic.ui.permissions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.streetmusic.R
import com.example.streetmusic.common.theme.StreetMusicTheme

@Composable
fun LoadPermissions() {
    Column(modifier = Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterHorizontally) {
        CircularProgressIndicator(color = StreetMusicTheme.colors.white)
        Text(
            text = stringResource(R.string.please_wait_perm),
            color = StreetMusicTheme.colors.white,
            style = StreetMusicTheme.typography.errorPermission
        )
    }
}