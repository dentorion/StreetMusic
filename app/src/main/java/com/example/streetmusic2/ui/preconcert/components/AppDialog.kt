package com.example.streetmusic2.ui.preconcert.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.streetmusic2.R

@Composable
fun AppDialog(
    modifier: Modifier = Modifier,
    dialogState: Boolean = false,
    onDialogPositiveButtonClicked: (() -> Unit)? = null,
    onDialogStateChange: ((Boolean) -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null,
) {
    val textPaddingAll = 24.dp
    val buttonPaddingAll = 8.dp
    val dialogShape = RoundedCornerShape(16.dp)

    if (dialogState) {
        AlertDialog(
            onDismissRequest = {
                onDialogStateChange?.invoke(false)
                onDismissRequest?.invoke()
            },
            title = null,
            text = null,
            buttons = {

                Column{
                    Image(
                        painter = painterResource(R.drawable.ic_concert_auth),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(Modifier.padding(all = textPaddingAll)){
                        Text(
                            text = stringResource(R.string.main_text)
                        )
                    }
                    Divider(color = MaterialTheme.colors.onSurface, thickness = 1.dp)

                    Row(
                        modifier = Modifier.padding(all = buttonPaddingAll),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onDialogStateChange?.invoke(false)
                                onDialogPositiveButtonClicked?.invoke()
                            }
                        ) {
                            Text(text = stringResource(R.string.dialog_ok_concert), color = MaterialTheme.colors.onSurface)
                        }
                    }
                }

            },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false),
            modifier = modifier,
            shape = dialogShape
        )
    }
}