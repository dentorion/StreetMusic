package com.entin.streetmusic.ui.start

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.entin.streetmusic.common.theme.StreetMusicTheme
import com.entin.streetmusic.ui.start.components.*
import com.entin.streetmusic.util.auth.createAnonymousUser
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun StartScreen(
    navToPermissions: () -> Unit,
) {
    createAnonymousUser()
    StartScreenContent(navToPermissions = navToPermissions)
}

@Composable
private fun StartScreenContent(navToPermissions: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Background image
        BackgroundImage()

        // Buttons
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding)
                .padding(bottom = StreetMusicTheme.dimensions.allBottomPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Logo()
            }
            // Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                contentAlignment = Alignment.TopCenter
            ) {
                Column {
                    FindMusicButton(navToPermissions = navToPermissions)
                    MusicianButton(navToPermissions = navToPermissions)
                }
            }
        }
    }
}