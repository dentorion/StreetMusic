package com.entin.streetmusic.ui.permissions.components.states

import androidx.compose.runtime.Composable
import com.entin.streetmusic.util.user.LocalUserPref

/**
 * Navigate to next Screen: Authorization or CityConcerts
 * depending what user chose before: find music or play
 */
@Composable
fun SuccessPermissions(
    navToAuthorization: () -> Unit,
    navToCityConcerts: () -> Unit
) {
    if (LocalUserPref.current.isMusician()) {
        navToAuthorization()
    } else {
        navToCityConcerts()
    }
}