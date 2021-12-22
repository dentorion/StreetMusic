package com.entin.streetmusic.ui.screens.permissions.states

import androidx.compose.runtime.Composable
import com.entin.streetmusic.data.user.LocalUserPref

/**
 * Navigate to next Screen: Authorization or CityConcerts
 * depending what user chose before: find music or play
 */

@Composable
fun SuccessPermissionsState(
    navToAuthorization: () -> Unit,
    navToCityConcerts: () -> Unit
) {
    if (LocalUserPref.current.isMusician()) {
        navToAuthorization()
    } else {
        navToCityConcerts()
    }
}