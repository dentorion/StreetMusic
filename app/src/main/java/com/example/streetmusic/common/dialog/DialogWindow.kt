package com.example.streetmusic.common.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.streetmusic.R
import com.example.streetmusic.common.dialog.components.DialogMapObserveDefault
import com.example.streetmusic.common.dialog.components.DialogPermissionsDefault
import com.example.streetmusic.common.model.dialog.DialogType
import com.example.streetmusic.common.theme.StreetMusicTheme

/**
 * Dialog Window
 * [dialogState] - show / hide dialog window
 * [dialogType] - screen where dialog is shown
 *
 * Depending on [dialogType] Dialog Window has different:
 * - icon
 * - title
 * - content @composable
 */

@Composable
fun DialogWindow(
    dialogState: MutableState<Boolean>,
    dialogType: DialogType,
    onOkClicked: (() -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null,
    content: @Composable () -> Unit = { DefaultContent(value = dialogType) },
) {
    when (dialogType) {
        is DialogType.Permissions -> {
            Dialog(
                dialogState = dialogState,
                onDialogPositiveButtonClicked = onOkClicked,
                onDismissRequest = onDismissRequest,
                title = "Location permissions",
                content = content,
            )
        }
        is DialogType.MapObserve -> {
            Dialog(
                dialogState = dialogState,
                onDialogPositiveButtonClicked = onOkClicked,
                onDismissRequest = onDismissRequest,
                title = "Musicians list",
                content = content,
            )
        }
    }
}

@Composable
private fun Dialog(
    dialogState: MutableState<Boolean>,
    onDialogPositiveButtonClicked: (() -> Unit)?,
    onDismissRequest: (() -> Unit)?,
    title: String,
    content: @Composable () -> Unit,
) {
    val buttonPaddingAll = 15.dp
    val dialogShape = RoundedCornerShape(16.dp)

    if (dialogState.value) {
        AlertDialog(
            onDismissRequest = {
                onDismissRequest?.invoke()
                dialogState.value = false
            },
            title = null,
            text = null,
            buttons = {
                Column(
                    modifier = Modifier.background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                StreetMusicTheme.colors.grayLight,
                                StreetMusicTheme.colors.white
                            )
                        )
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(all = buttonPaddingAll),
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )

                    content.invoke()

                    Divider(color = StreetMusicTheme.colors.grayDark, thickness = 1.dp)

                    Row(
                        modifier = Modifier.padding(all = buttonPaddingAll),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onDialogPositiveButtonClicked?.invoke()
                                dialogState.value = false
                            }
                        ) {
                            Text(text = "OK", color = StreetMusicTheme.colors.grayDark)
                        }
                    }
                }

            },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            shape = dialogShape
        )
    }
}

@Composable
private fun DefaultContent(value: DialogType) {
    when (value) {
        is DialogType.Permissions -> {
            DialogPermissionsDefault(R.drawable.ic_marker_location)
        }
        is DialogType.MapObserve -> {
            DialogMapObserveDefault(R.drawable.ic_marker_location)
        }
    }
}