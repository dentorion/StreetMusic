package com.example.streetmusic2.ui.preconcert.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DescriptionTextField(
    caption: String,
    value: String,
    onChange: (String) -> Unit,
) {
    TextFieldPreConcert(
        caption = caption,
        maxLength = 101,
        singleLine = true,
        maxLines = 1,
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
        maxLength = 50,
        singleLine = true,
        maxLines = 1,
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
        maxLength = 25,
        singleLine = false,
        maxLines = 4,
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
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            color = Color.White
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = input,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                cursorColor = Color.Black,
                disabledLabelColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
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
            shape = MaterialTheme.shapes.medium,
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
                .padding(top = 4.dp),
            textAlign = TextAlign.End,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}