package com.example.streetmusic2.ui.preconcert.components

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
import com.example.streetmusic2.util.constant.START_CONCERT
import com.example.streetmusic2.util.user.LocalUserPref

@Composable
fun RunConcertButton(
    onClick: () -> Unit,
    uID: String,
) {
    val userPref = LocalUserPref.current

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(vertical = 10.dp),
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
            text = START_CONCERT,
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.primary
        )
    }
}