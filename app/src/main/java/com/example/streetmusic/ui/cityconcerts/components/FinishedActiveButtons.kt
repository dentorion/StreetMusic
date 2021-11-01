package com.example.streetmusic.ui.cityconcerts.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.streetmusic.R
import com.example.streetmusic.ui.cityconcerts.CityConcertsViewModel
import com.example.streetmusic.common.theme.StreetMusicTheme
import java.util.*

@Composable
fun FinishedActiveButtons(
    viewModel: CityConcertsViewModel,
    isButtonEnabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        SortStyleFinishedActualConcertsByCity(
            onFAClick = { viewModel.switchFinishActive(it) },
            mode = false,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = StreetMusicTheme.dimensions.paddingBetweenButtons)
                .padding(vertical = StreetMusicTheme.dimensions.buttonsVerticalPadding),
            actualFAStyle = viewModel.stateFinishedActiveConcerts,
            enabled = isButtonEnabled
        )
        SortStyleFinishedActualConcertsByCity(
            onFAClick = { viewModel.switchFinishActive(it) },
            mode = true,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = StreetMusicTheme.dimensions.paddingBetweenButtons)
                .padding(vertical = StreetMusicTheme.dimensions.buttonsVerticalPadding),
            actualFAStyle = viewModel.stateFinishedActiveConcerts,
            enabled = isButtonEnabled
        )
    }
}

@Composable
private fun SortStyleFinishedActualConcertsByCity(
    modifier: Modifier,
    onFAClick: (Boolean) -> Unit,
    mode: Boolean,
    actualFAStyle: Boolean,
    enabled: Boolean,
) {
    var border: BorderStroke? = null
    /**
     * Border for active button is if: [onFAClick] is chosen by user and Active button rendering
     * Border for finished button is if: [onFAClick] is Not chosen by user and Finish button rendering
     */
    if (actualFAStyle && mode || !actualFAStyle && !mode) {
        border = BorderStroke(
            StreetMusicTheme.dimensions.borderStrokeActiveButton,
            StreetMusicTheme.colors.divider
        )
    }

    OutlinedButton(
        modifier = modifier,
        border = border,
        onClick = {
            if (mode) {
                onFAClick(true)
            } else {
                onFAClick(false)
            }
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = StreetMusicTheme.colors.white,
            contentColor = StreetMusicTheme.colors.grayDark
        ),
        shape = StreetMusicTheme.shapes.medium,
        enabled = enabled
    ) {
        Text(
            text = if (mode) {
                stringResource(id = R.string.active_concerts_label).uppercase(Locale.getDefault())
            } else {
                stringResource(id = R.string.finished_concerts_label).uppercase(Locale.getDefault())
            },
            style = StreetMusicTheme.typography.buttonText
        )
    }
}