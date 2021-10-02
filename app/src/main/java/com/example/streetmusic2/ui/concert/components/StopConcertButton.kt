package com.example.streetmusic2.ui.concert.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.util.constant.STOP_CONCERT
import com.example.streetmusic2.util.userpref.LocalUserPref

@Composable
fun StopConcertButton(
    onClick: () -> Unit,
    uID: String,
) {
    val userPref = LocalUserPref.current

    Button(
        modifier = Modifier
            .padding(vertical = 15.dp)
            .fillMaxWidth(),
        onClick = {
            userPref.setId(uID)
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = STOP_CONCERT,
            style = MaterialTheme.typography.button,
            color = Color.Red
        )
    }
}