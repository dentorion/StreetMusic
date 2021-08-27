package com.example.streetmusic2.ui.start.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.util.userpref.LocalUserPref

@Composable
fun FindMusicButton(navToPermissions: () -> Unit) {
    val userPref = LocalUserPref.current
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(bottom = 16.dp),
        onClick = {
            userPref.setIsMusician(false)
            navToPermissions()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = "FIND GOOD STREET MUSIC",
            style = MaterialTheme.typography.button
        )
    }
}
