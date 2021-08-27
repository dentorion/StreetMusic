package com.example.streetmusic2.ui.start.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.streetmusic2.util.userpref.LocalUserPref

@Composable
fun MusicianButton(navToPermissions: () -> Unit) {
    val userPref = LocalUserPref.current
    TextButton(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            userPref.setIsMusician(true)
            navToPermissions()
        },
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colors.onPrimary
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = "I'M PLAYING NOW",
            style = MaterialTheme.typography.button
        )
    }
}
