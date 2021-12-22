package com.entin.streetmusic.ui.screens.preconcert.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.entin.streetmusic.R
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.ui.screens.preconcert.PreConcertViewModel
import com.entin.streetmusic.ui.screens.preconcert.constant.*

@Composable
fun TextFields(
    viewModel: PreConcertViewModel,
) {
    BandNameTextField(
        caption = stringResource(id = R.string.label_name),
        value = viewModel.bandName,
        onChange = {
            viewModel.setCurrentBandName(it)
        })
    ConcertAddressTextField(
        caption = stringResource(id = R.string.label_address),
        value = viewModel.address,
        onChange = {
            viewModel.setCurrentAddress(it)
        })
    DescriptionTextField(
        caption = stringResource(id = R.string.label_description),
        value = viewModel.description,
        onChange = {
            viewModel.setCurrentDescription(it)
        })
}

@Composable
fun DescriptionTextField(
    caption: String,
    value: String,
    onChange: (String) -> Unit,
) {
    TextFieldPreConcert(
        caption = caption,
        maxLength = DESCRIPTION_MAX_LENGTH,
        singleLine = true,
        maxLines = MAX_LINES_FIELD_ONE,
        input = value,
        onChange = onChange,
    )
}

@Composable
fun ConcertAddressTextField(
    caption: String,
    value: String,
    onChange: (String) -> Unit,
) {
    TextFieldPreConcert(
        caption = caption,
        maxLength = CAPTION_MAX_LENGTH,
        singleLine = true,
        maxLines = MAX_LINES_FIELD_ONE,
        input = value,
        onChange = onChange,
    )
}

@Composable
fun BandNameTextField(
    caption: String,
    value: String,
    onChange: (String) -> Unit,
) {
    TextFieldPreConcert(
        caption = caption,
        maxLength = BAND_NAME_MAX_LENGTH,
        singleLine = false,
        maxLines = MAX_LINES_FIELD_FOUR,
        input = value,
        onChange = onChange,
    )
}

@Composable
private fun TextFieldPreConcert(
    caption: String,
    maxLength: Int,
    singleLine: Boolean,
    maxLines: Int,
    input: String,
    onChange: (String) -> Unit,
) {
    Column {
        Text(
            text = caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = StreetMusicTheme.dimensions.labelPadding),
            textAlign = TextAlign.Start,
            style = StreetMusicTheme.typography.labelField,
            color = StreetMusicTheme.colors.white
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = input,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = StreetMusicTheme.colors.white,
                cursorColor = StreetMusicTheme.colors.grayDark,
                disabledLabelColor = StreetMusicTheme.colors.white,
                focusedIndicatorColor = StreetMusicTheme.colors.transparent,
                unfocusedIndicatorColor = StreetMusicTheme.colors.transparent
            ),
            onValueChange = {
                if (it.length <= maxLength) {
                    onChange(it)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = input.isEmpty(),
            shape = StreetMusicTheme.shapes.medium,
            singleLine = singleLine,
            maxLines = maxLines,
            trailingIcon = {
                if (input.isNotEmpty()) {
                    IconButton(onClick = { onChange("") }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        tint = Color.Magenta,
                        contentDescription = null
                    )
                }
            }
        )
        Text(
            text = "${input.length} / $maxLength",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = StreetMusicTheme.dimensions.labelPadding),
            textAlign = TextAlign.End,
            style = StreetMusicTheme.typography.labelField,
            color = StreetMusicTheme.colors.white
        )
    }
}