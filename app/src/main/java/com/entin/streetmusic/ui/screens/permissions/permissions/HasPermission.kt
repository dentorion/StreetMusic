package com.entin.streetmusic.ui.screens.permissions.permissions

import androidx.compose.runtime.Composable
import com.entin.streetmusic.core.model.domain.ConcertDomain
import com.entin.streetmusic.core.model.response.SMResponse
import com.entin.streetmusic.ui.screens.permissions.states.ErrorPermissionsState
import com.entin.streetmusic.ui.screens.permissions.states.InitialPermissionsState
import com.entin.streetmusic.ui.screens.permissions.states.LoadPermissionsState
import com.entin.streetmusic.ui.screens.permissions.states.SuccessPermissionsState
import timber.log.Timber

@Composable
fun HasPermission(
    navToAuthorization: () -> Unit,
    navToCityConcerts: () -> Unit,
    uiState: SMResponse<ConcertDomain>,
    getUserInfo: () -> Unit,
) {
    /**
     * Check UI State
     */
    when (uiState) {
        is SMResponse.ErrorResponse -> {
            Timber.i("PermissionsContent.Error")
            ErrorPermissionsState(uiState.SMError)
        }
        is SMResponse.LoadResponse -> {
            Timber.i("PermissionsContent.Load")
            LoadPermissionsState()
        }
        is SMResponse.SuccessResponse -> {
            Timber.i("PermissionsContent.Success")
            SuccessPermissionsState(
                navToAuthorization = navToAuthorization,
                navToCityConcerts = navToCityConcerts
            )
        }
        is SMResponse.InitialResponse -> {
            Timber.i("PermissionsContent.Initial")
            InitialPermissionsState(getUserInfo)
        }
    }
}