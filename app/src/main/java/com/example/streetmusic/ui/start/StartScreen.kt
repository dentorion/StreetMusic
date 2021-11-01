package com.example.streetmusic.ui.start

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.streetmusic.ui.start.components.BackgroundImage
import com.example.streetmusic.ui.start.components.FindMusicButton
import com.example.streetmusic.ui.start.components.Logo
import com.example.streetmusic.ui.start.components.MusicianButton
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun StartScreen(
    navToPermissions: () -> Unit,
) {
    Log.i("MyMusic", "5.StartScreen")
    StartContent(navToPermissions = navToPermissions)
}

@Composable
fun StartContent(navToPermissions: () -> Unit) {
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