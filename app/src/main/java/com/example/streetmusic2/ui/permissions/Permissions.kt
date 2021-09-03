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
import com.example.streetmusic2.R
import com.example.streetmusic2.common.model.Concert
import com.example.streetmusic2.common.model.MyResponse
import com.example.streetmusic2.ui.permissions.components.LocationMusicLogo
import com.example.streetmusic2.ui.permissions.components.ShowIndicatorLoad
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
    viewModel: PermissionsViewModel,
    navToCityConcerts: () -> Unit,
    navToAuthorization: () -> Unit
) {
    val state = viewModel.state
    val userPref = LocalUserPref.current
    val mapPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    PermissionsContent(
        state = state,
        userPref = userPref,
        navToCityConcerts = navToCityConcerts,
        navToAuthorization = navToAuthorization,
        mapPermission = mapPermissionState,
        getUserCity = { viewModel.getUserCity() }
    )
}

@ExperimentalPermissionsApi
@Composable
fun PermissionsContent(
    state: MyResponse<Concert>,
    userPref: UserSharedPreferences,
    navToCityConcerts: () -> Unit,
    navToAuthorization: () -> Unit,
    mapPermission: PermissionState,
    getUserCity: () -> Unit
) {
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
            LocationMusicLogo()
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
                    .padding(bottom = 42.dp),
            ) {
                when {
                    mapPermission.hasPermission -> {
                        when (state) {
                            is MyResponse.Error -> {
                                Log.e("MyMusic", stringResource(R.string.error_location))
                                DisableSelection {
                                    Text(
                                        text = stringResource(R.string.error_location),
                                        color = Color.Black,
                                        maxLines = 1
                                    )
                                }
                            }
                            is MyResponse.Load -> {
                                ShowIndicatorLoad(0.75f)
                            }
                            is MyResponse.Success -> {
                                ShowIndicatorLoad(1.0f)
                                setUserPref(userPref, state)
                                if (userPref.isMusician()) {
                                    navToAuthorization()
                                } else {
                                    navToCityConcerts()
                                }
                            }
                            is MyResponse.Initial -> {
                                ShowIndicatorLoad(0.25f)
                                getUserCity()
                            }
                        }
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
                                text = stringResource(R.string.localize_me),
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
                                text = stringResource(R.string.why_question),
                                style = MaterialTheme.typography.button
                            )
                        }
                    }

                    else -> {
                        DisableSelection {
                            Text(
                                text = stringResource(R.string.need_permission),
                                color = Color.White,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun setUserPref(
    userPref: UserSharedPreferences,
    state: MyResponse.Success<Concert>
) {
    userPref.setCity(state.data.city)
    userPref.setCountry(state.data.country)
    userPref.setLatitude(state.data.latitude)
    userPref.setLongitude(state.data.longitude)
}