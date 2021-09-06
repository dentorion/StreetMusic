package com.example.streetmusic2.ui.cityconcerts.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun SortStyleAllConcertsByCity(
    userCity: String,
    onAllClick: () -> Unit,
    actualAllStyle: Boolean,
    enabled: Boolean
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(6.dp),
        border = if (actualAllStyle) {
            BorderStroke(4.dp, MaterialTheme.colors.primary)
        } else {
            null
        },
        onClick = {
            onAllClick()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ),
        shape = MaterialTheme.shapes.medium,
        enabled = enabled
    ) {
        Text(
            text = "All concerts in " + userCity.uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.button
        )
    }
}