package com.example.streetmusic2.ui.start.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.util.constant.IM_ARTIST
import com.example.streetmusic2.util.user.LocalUserPref

@Composable
fun MusicianButton(navToPermissions: () -> Unit) {
    val userPref = LocalUserPref.current
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
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
            text = IM_ARTIST,
            style = MaterialTheme.typography.button
        )
    }
}
