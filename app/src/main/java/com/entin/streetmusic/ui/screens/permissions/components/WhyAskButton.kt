package com.entin.streetmusic.ui.screens.permissions.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.core.theme.StreetMusicTheme

@Composable
fun WhyAskButton(openDialog: MutableState<Boolean>) {
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = StreetMusicTheme.dimensions.buttonsVerticalPadding),
        onClick = {
            openDialog.value = true
        },
        shape = StreetMusicTheme.shapes.medium
    ) {
        Text(
            text = stringResource(R.string.why_question_button_label),
            color = StreetMusicTheme.colors.white,
            style = StreetMusicTheme.typography.buttonText
        )
    }
}