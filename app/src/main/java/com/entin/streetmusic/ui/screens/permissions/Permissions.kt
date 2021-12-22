package com.entin.streetmusic.ui.screens.permissions

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.entin.streetmusic.ui.screens.permissions.permissions.AskPermissions
import com.entin.streetmusic.ui.screens.permissions.permissions.HasPermission
import com.entin.streetmusic.ui.screens.permissions.permissions.ErrorPermissions
import com.entin.streetmusic.ui.screens.start.components.BackgroundImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@Composable
fun Permissions(
    viewModel: PermissionsViewModel = hiltViewModel(),
    navToCityConcerts: () -> Unit,
    navToAuthorization: () -> Unit
) {
    Timber.i("Permissions")

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
            mapPermissionState.hasPermission -> HasPermission(
                navToAuthorization = navToAuthorization,
                navToCityConcerts = navToCityConcerts,
                uiState = viewModel.uiStatePermissions,
                getUserInfo = viewModel::getUserInfo,
            )

            mapPermissionState.shouldShowRationale ||
                    !mapPermissionState.permissionRequested -> AskPermissions(mapPermissionState)

            else -> ErrorPermissions()
        }
    }
}