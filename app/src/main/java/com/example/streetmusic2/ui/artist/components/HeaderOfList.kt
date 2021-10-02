package com.example.streetmusic2.ui.artist.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HeaderOfList(
    online: Boolean,
    caption: String
) {
    val color = if (online) {
        Color.Green
    } else {
        Color.Red
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(vertical = 25.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            IconCircle(color)
        }
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = caption,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}