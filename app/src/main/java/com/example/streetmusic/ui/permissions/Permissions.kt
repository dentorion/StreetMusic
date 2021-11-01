package com.example.streetmusic.ui.permissions

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streetmusic.common.dialog.DialogWindow
import com.example.streetmusic.common.model.dialog.DialogType
import com.example.streetmusic.common.model.domain.ConcertDomain
import com.example.streetmusic.common.model.vmstate.CommonResponse
import com.example.streetmusic.ui.permissions.components.*
import com.example.streetmusic.ui.start.components.BackgroundImage
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.example.streetmusic.util.user.LocalUserPref
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@Composable
fun Permissions(
    viewModel: PermissionsViewModel = hiltViewModel(),
    navToCityConcerts: () -> Unit,
    navToAuthorization: () -> Unit
) {
    Log.i("MyMusic", "4.Permissions")

    /**
     * PERMISSIONS for map
     */
    val mapPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BackgroundImage()
        /**
         * Checking permissions state:
         * - has permissions
         * - need to get permissions
         * - message for declined permissions
         */
        when {
            mapPermissionState.hasPermission -> {
                HasPermission(
                    navToAuthorization = navToAuthorization,
                    navToCityConcerts = navToCityConcerts,
                    uiState = viewModel.state,
                    getUserInfo = viewModel::getUserInfo,
                )
            }

            mapPermissionState.shouldShowRationale || !mapPermissionState.permissionRequested -> {
                AskPermissions(mapPermissionState)
            }

            else -> {
                ErrorPermissions()
            }
        }
    }
}

@Composable
private fun HasPermission(
    navToAuthorization: () -> Unit,
    navToCityConcerts: () -> Unit,
    uiState: CommonResponse<ConcertDomain>,
    getUserInfo: () -> Unit,
) {
    /**
     * Check UI State
     */
    when (uiState) {
        is CommonResponse.Error -> {
            Log.i("MyMusic", "4.PermissionsContent.Error")
            ErrorPermissions()
        }
        is CommonResponse.Load -> {
            Log.i("MyMusic", "4.PermissionsContent.Load")
            LoadPermissions()
        }
        is CommonResponse.Success -> {
            Log.i("MyMusic", "4.PermissionsContent.Success")
            SuccessPermissions(
                navToAuthorization = navToAuthorization,
                navToCityConcerts = navToCityConcerts
            )
        }
        is CommonResponse.Initial -> {
            Log.i("MyMusic", "4.PermissionsContent.Initial")
            InitialPermissions(getUserInfo)
        }
    }
}

@Composable
private fun InitialPermissions(getUserInfo: () -> Unit) {
    getUserInfo()
}

/**
 * Navigate to next Screen: Authorization or CityConcerts
 * depending what user chose before: find music or play
 */
@Composable
private fun SuccessPermissions(
    navToAuthorization: () -> Unit,
    navToCityConcerts: () -> Unit
) {
    if (LocalUserPref.current.isMusician()) {
        navToAuthorization()
    } else {
        navToCityConcerts()
    }
}

/**
 * Ask permissions from the user
 */
@ExperimentalPermissionsApi
@Composable
private fun AskPermissions(mapPermissionState: PermissionState) {
    Log.i("MyMusic", "4.PermissionsContent.Show Button")
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
                        dialogState = openDialog,
                        dialogType = DialogType.Permissions()
                    )
                }
            }
        }
    }
}