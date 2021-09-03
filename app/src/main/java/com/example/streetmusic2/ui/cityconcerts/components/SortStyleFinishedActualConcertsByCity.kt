package com.example.streetmusic2.ui.cityconcerts.components

import androidx.compose.foundation.BorderStroke
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
fun SortStyleFinishedActualConcertsByCity(
    onFAClick: (Boolean) -> Unit,
    mode: Boolean,
    modifier: Modifier,
    actualFAStyle: Boolean
) {
    var border: BorderStroke? = null
    if (actualFAStyle && mode) {
        border = BorderStroke(4.dp, MaterialTheme.colors.primary)
    }
    if (!actualFAStyle && !mode) {
        border = BorderStroke(4.dp, MaterialTheme.colors.primary)
    }

    OutlinedButton(
        modifier = modifier
            .height(55.dp)
            .padding(6.dp),
        border = border,
        onClick = {
            if (mode) {
                onFAClick(true)
            } else {
                onFAClick(false)
            }
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = if (mode) {
                "Active".uppercase(Locale.getDefault())
            } else {
                "Finished".uppercase(Locale.getDefault())
            },
            style = MaterialTheme.typography.button
        )
    }
}
