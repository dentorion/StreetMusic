package com.example.streetmusic2.ui.permissions

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streetmusic2.R
import com.example.streetmusic2.common.model.concert.ConcertFirebase
import com.example.streetmusic2.common.model.responce.CommonResponse
import com.example.streetmusic2.ui.permissions.components.HasPermissionLoad
import com.example.streetmusic2.ui.permissions.components.LocationMusicLogo
import com.example.streetmusic2.ui.start.components.BackgroundImage
import com.example.streetmusic2.util.userpref.LocalUserPref
import com.example.streetmusic2.util.userpref.UserSharedPreferences
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
                    userPref = LocalUserPref.current,
                    getUserCity = viewModel::getUserCity,
                )
            }

            mapPermissionState.shouldShowRationale || !mapPermissionState.permissionRequested -> {
                AskPermissions(mapPermissionState)
            }

            else -> {
                NoPermissions()
            }
        }
    }
}

@Composable
private fun HasPermission(
    navToAuthorization: () -> Unit,
    navToCityConcerts: () -> Unit,
    uiState: CommonResponse<ConcertFirebase>,
    userPref: UserSharedPreferences,
    getUserCity: () -> Unit,
) {
    /**
     * Check UI State
     */
    when (uiState) {
        is CommonResponse.Error -> {
            HasPermissionError()
        }
        is CommonResponse.Load -> {
            Log.i("MyMusic", "4.PermissionsContent.Load")
            HasPermissionLoad()
        }
        is CommonResponse.Success -> {
            Log.i("MyMusic", "4.PermissionsContent.Success")
            HasPermissionLoad()
            setUserPref(userPref, uiState)
            if (userPref.isMusician()) {
                navToAuthorization()
            } else {
                navToCityConcerts()
            }
        }
        is CommonResponse.Initial -> {
            Log.i("MyMusic", "4.PermissionsContent.Initial")
            HasPermissionLoad()
            getUserCity()
        }
    }
}

@Composable
private fun HasPermissionError() {
    Log.i("MyMusic", "4.PermissionsContent.Error")
    Log.e("MyMusic", stringResource(R.string.error_location))
    DisableSelection {
        Text(
            text = stringResource(R.string.error_location),
            color = Color.Black,
            maxLines = 1
        )
    }
}

@ExperimentalPermissionsApi
@Composable
private fun AskPermissions(mapPermissionState: PermissionState) {
    Log.i("MyMusic", "4.PermissionsContent.Show Button")
    Log.i("MyMusic", "4.PermissionsContent")

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.weight(8f), contentAlignment = Alignment.Center) {
            LocationMusicLogo()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    onClick = { mapPermissionState.launchPermissionRequest() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        contentColor = MaterialTheme.colors.onSurface
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(R.string.localize_me),
                        style = MaterialTheme.typography.button
                    )
                }
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    onClick = { },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colors.onPrimary
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(R.string.why_question),
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}

@Composable
private fun NoPermissions() {
    Log.i("MyMusic", "4.PermissionsContent.DisableSelection")
    DisableSelection {
        Text(
            text = stringResource(R.string.need_permission),
            color = Color.White,
            maxLines = 1
        )
    }
}

private fun setUserPref(
    userPref: UserSharedPreferences,
    state: CommonResponse.Success<ConcertFirebase>
) {
    try {
        userPref.setCity(state.data.city)
        userPref.setCountry(state.data.country)
        userPref.setLatitude(state.data.latitude)
        userPref.setLongitude(state.data.longitude)
    } catch (e: Exception) {
        Log.i("MyMusic", "Error while user prefs are written")
    }

}