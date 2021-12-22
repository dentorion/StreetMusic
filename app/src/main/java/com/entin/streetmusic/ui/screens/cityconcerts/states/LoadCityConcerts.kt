package com.entin.streetmusic.ui.screens.cityconcerts.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.core.theme.StreetMusicTheme
import timber.log.Timber

@Composable
fun LoadCityConcerts() {
    Timber.i("CityConcertsContent.Load")
    Column(modifier = Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        CircularProgressIndicator(color = StreetMusicTheme.colors.mainFirst)
        DisableSelection {
            Text(
                text = stringResource(R.string.please_wait_perm),
                color = StreetMusicTheme.colors.mainFirst,
                style = StreetMusicTheme.typography.errorPermission
            )
        }
    }
}