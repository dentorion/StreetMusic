package com.example.streetmusic2.ui.permissions

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.ui.start.components.BackgroundImage
import com.example.streetmusic2.ui.start.components.Logo
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
@Composable
fun Permissions(
    viewModel: PermissionsViewModel,
    navToCityConcerts: () -> Unit,
    navToAuthorization: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val mapPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    PermissionsContent(
        state = state,
        navToCityConcerts = navToCityConcerts,
        navToAuthorization = navToAuthorization,
        mapPermission = mapPermissionState
    )
}

@ExperimentalPermissionsApi
@Composable
fun PermissionsContent(
    state: State<Int>,
    navToCityConcerts: () -> Unit,
    navToAuthorization: () -> Unit,
    mapPermission: PermissionState,
) {
    Log.i("MyMusic", "Permissions State : $state")
    BackgroundImage()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Logo()
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 42.dp)
            ) {
                when {
                    mapPermission.hasPermission -> {



                    }

                    mapPermission.shouldShowRationale || !mapPermission.permissionRequested -> {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 16.dp)
                                .height(55.dp),
                            onClick = { mapPermission.launchPermissionRequest() },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.surface,
                                contentColor = MaterialTheme.colors.onSurface
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = "LOCALIZE ME",
                                style = MaterialTheme.typography.button
                            )
                        }

                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            onClick = { },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colors.onPrimary
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = "Why do we ask this?",
                                style = MaterialTheme.typography.button
                            )
                        }
                    }

                    else -> {
                        Text(text = "Нам нужно получить Ваши координаты!")
                    }
                }
            }
        }
    }
}