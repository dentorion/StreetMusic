package com.entin.streetmusic.ui.screens.authorizate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.entin.streetmusic.core.model.response.SMResponse
import com.entin.streetmusic.ui.screens.authorizate.states.ErrorAuth
import com.entin.streetmusic.ui.screens.authorizate.states.InitialAuth
import com.entin.streetmusic.ui.screens.authorizate.states.LoadAuth
import com.entin.streetmusic.ui.screens.authorizate.states.SuccessAuth
import com.entin.streetmusic.ui.screens.start.components.BackgroundImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber

@Composable
fun Authorize(
    viewModel: AuthorizeViewModel = hiltViewModel(),
    navToPreConcert: (String) -> Unit,
    navToConcert: (artistId: String, documentId: String) -> Unit,
) {
    Timber.i("Authorize")

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BackgroundImage()

        AuthorizeContent(
            state = viewModel.uiStateAuthorize,
            viewModel = viewModel,
            navToPreConcert = navToPreConcert,
            navToConcert = navToConcert
        )
    }
}

@Composable
private fun AuthorizeContent(
    state: SMResponse<InsideResponseAuthorize>,
    viewModel: AuthorizeViewModel,
    navToPreConcert: (String) -> Unit,
    navToConcert: (artistId: String, documentId: String) -> Unit,
) {
    when (state) {
        is SMResponse.ErrorResponse -> {
            Timber.i("Authorize.E")
            ErrorAuth(state.SMError)
        }

        is SMResponse.InitialResponse -> {
            Timber.i("Authorize.I")
            InitialAuth(
                currentUser = Firebase.auth.currentUser,
                signIn = { viewModel.linkAnonymousToAccount(it) },
                checkOnLineConcertsByUser = { viewModel.checkOnlineConcertByUser(it) },
            )
        }

        is SMResponse.LoadResponse -> {
            Timber.i("Authorize.L")
            LoadAuth()
        }

        is SMResponse.SuccessResponse -> {
            Timber.i("Authorize.S")
            SuccessAuth(
                uiState = state.data,
                checkOnlineConcertByUser = { viewModel.checkOnlineConcertByUser(state.data.artistId) },
                navToPreConcert = navToPreConcert,
                navToConcert = navToConcert,
            )
        }
    }
}