package com.example.streetmusic2.ui.start

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.ui.start.components.BackgroundImage
import com.example.streetmusic2.ui.start.components.FindMusicButton
import com.example.streetmusic2.ui.start.components.Logo
import com.example.streetmusic2.ui.start.components.MusicianButton

@Composable
fun StartScreen(
    viewModel: StartScreenViewModel,
    navToPermissions: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    StartContent(state, navToPermissions)
}

@Composable
fun StartContent(
    state: State<Int>,
    navToPermissions: () -> Unit
) {
    Log.i("MyMusic", "State : $state")
    BackgroundImage()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Logo()
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 42.dp)
            ) {
                FindMusicButton(navToPermissions = navToPermissions)
                MusicianButton(navToPermissions = navToPermissions)
            }
        }
    }
}