package com.entin.streetmusic.ui.screens.permissions.permissions

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.entin.streetmusic.core.dialog.DialogWindow
import com.entin.streetmusic.core.model.dialog.PermissionsDialog
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.ui.screens.permissions.components.LocalizeMeButton
import com.entin.streetmusic.ui.screens.permissions.components.LocationMusicLogo
import com.entin.streetmusic.ui.screens.permissions.components.WhyAskButton
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import timber.log.Timber

/**
 * Ask permissions from the user
 */
@ExperimentalPermissionsApi
@Composable
fun AskPermissions(mapPermissionState: PermissionState) {
    Timber.i("PermissionsContent.Show Button")
    val openDialog = remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding)
            .padding(vertical = StreetMusicTheme.dimensions.allBottomPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            LocationMusicLogo()
        }
        // Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                // Localize me button
                LocalizeMeButton(mapPermissionState)

                // Why we ask button
                WhyAskButton(openDialog)

                // Dialog to show why we ask permissions
                if (openDialog.value) {
                    DialogWindow(
                        dialogType = PermissionsDialog(),
                        openDialogState = openDialog,
                    )
                }
            }
        }
    }
}