package com.entin.streetmusic.ui.permissions

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.entin.streetmusic.common.model.domain.ConcertDomain
import com.entin.streetmusic.common.model.vmstate.CommonResponse
import com.entin.streetmusic.ui.permissions.components.AskPermissions
import com.entin.streetmusic.ui.permissions.components.states.*
import com.entin.streetmusic.ui.start.components.BackgroundImage
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
            Timber.i("PermissionsContent.Error")
            ErrorPermissions()
        }
        is CommonResponse.Load -> {
            Timber.i("PermissionsContent.Load")
            LoadPermissions()
        }
        is CommonResponse.Success -> {
            Timber.i("PermissionsContent.Success")
            SuccessPermissions(
                navToAuthorization = navToAuthorization,
                navToCityConcerts = navToCityConcerts
            )
        }
        is CommonResponse.Initial -> {
            Timber.i("PermissionsContent.Initial")
            InitialPermissions(getUserInfo)
        }
    }
}