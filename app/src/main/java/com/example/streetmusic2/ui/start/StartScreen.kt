package com.example.streetmusic2.ui.start

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.ui.start.components.BackgroundImage
import com.example.streetmusic2.ui.start.components.FindMusicButton
import com.example.streetmusic2.ui.start.components.Logo
import com.example.streetmusic2.ui.start.components.MusicianButton

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
        BackgroundImage()

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.weight(8f), contentAlignment = Alignment.Center) {
                Logo()
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
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