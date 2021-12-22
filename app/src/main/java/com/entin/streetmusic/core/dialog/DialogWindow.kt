package com.entin.streetmusic.core.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.entin.streetmusic.R
import com.entin.streetmusic.core.dialog.components.DialogMapObserveDefault
import com.entin.streetmusic.core.dialog.components.DialogPermissionsDefault
import com.entin.streetmusic.core.dialog.components.DialogPreConcertAvatarDefault
import com.entin.streetmusic.core.dialog.components.DialogPreConcertErrorDefault
import com.entin.streetmusic.core.model.dialog.*
import com.entin.streetmusic.core.theme.StreetMusicTheme

/**
 * Dialog Window
 * openDialogState - show / hide dialog window
 * [dialogType] - screen where dialog is shown
 *
 * Depending on [dialogType] Dialog Window has different:
 * - icon
 * - title
 * - content @composable
 */

@Composable
fun DialogWindow(
    dialogType: DialogType,
    onOkClicked: (() -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null,
    content: @Composable () -> Unit = { DefaultContent(value = dialogType) },
    openDialogState: MutableState<Boolean>,
) {
    Dialog(
        dialogState = openDialogState,
        onDialogPositiveButtonClicked = onOkClicked,
        onDismissRequest = onDismissRequest,
        title = stringResource(id = dialogType.title),
        content = content,
    )
}

@Composable
private fun Dialog(
    dialogState: MutableState<Boolean>,
    onDialogPositiveButtonClicked: (() -> Unit)?,
    onDismissRequest: (() -> Unit)?,
    title: String,
    content: @Composable () -> Unit,
) {
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
                    /**
                     * Header
                     */
                    Text(
                        modifier = Modifier.padding(all = StreetMusicTheme.dimensions.allHorizontalPadding),
                        text = title,
                        style = StreetMusicTheme.typography.dialogHeader,
                    )
                    /**
                     * Body
                     */
                    content.invoke()
                    /**
                     * Line horizontal
                     */
                    Divider(
                        color = StreetMusicTheme.colors.grayLight,
                        thickness = StreetMusicTheme.dimensions.dividerThicknessDialog
                    )
                    /**
                     * Button
                     */
                    Row(
                        modifier = Modifier.padding(all = StreetMusicTheme.dimensions.allHorizontalPadding),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                /**
                                 * OK button onClick
                                 */
                                onDialogPositiveButtonClicked?.invoke()
                                dialogState.value = false
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.dialog_ok_button),
                                color = StreetMusicTheme.colors.grayDark
                            )
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
        is PermissionsDialog -> DialogPermissionsDefault(value.icon)
        is MapObserveDialog -> DialogMapObserveDefault()
        is PreConcertFieldDialog -> DialogPreConcertErrorDefault(value.icon)
        is PreConcertAvatarDialog -> DialogPreConcertAvatarDefault(value.icon)
    }
}