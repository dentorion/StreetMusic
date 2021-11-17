package com.entin.streetmusic.ui.authorizate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.entin.streetmusic.R
import com.entin.streetmusic.common.model.response.StreetMusicResponse
import com.entin.streetmusic.common.theme.StreetMusicTheme
import com.entin.streetmusic.ui.authorizate.components.AuthorizationContent
import com.entin.streetmusic.ui.start.components.BackgroundImage
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber

@Composable
fun Authorize(
    viewModel: AuthorizeViewModel = hiltViewModel(),
    navToPreConcert: (String) -> Unit,
    navToConcert: (artistId: String, documentId: String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BackgroundImage()

        Timber.i("Authorize")
        val uiAuthorizationState = viewModel.uiStateAuthorize

        AuthorizeContent(
            state = uiAuthorizationState,
            viewModel = viewModel,
            navToPreConcert = navToPreConcert,
            navToConcert = navToConcert
        )
    }
}

@Composable
private fun AuthorizeContent(
    state: StreetMusicResponse<InsideResponse>,
    viewModel: AuthorizeViewModel,
    navToPreConcert: (String) -> Unit,
    navToConcert: (artistId: String, documentId: String) -> Unit,
) {
    when (state) {
        is StreetMusicResponse.Error -> {
            Timber.i("Authorize.E")
            ErrorAuth(state.message)
        }

        is StreetMusicResponse.Initial -> {
            Timber.i("Authorize.I")
            InitialAuth(
                currentUser = Firebase.auth.currentUser,
                signIn = { viewModel.linkAnonymousToAccount(it) },
                checkOnLineConcertsByUser = { viewModel.checkOnlineConcertByUser(it) },
            )
        }

        is StreetMusicResponse.Load -> {
            Timber.i("Authorize.L")
            LoadAuth()
        }

        is StreetMusicResponse.Success -> {
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

@Composable
private fun SuccessAuth(
    uiState: InsideResponse,
    checkOnlineConcertByUser: () -> Unit,
    navToPreConcert: (String) -> Unit,
    navToConcert: (artistId: String, documentId: String) -> Unit,
) {
    if (uiState.navigateConcert.not() && uiState.navigatePreConcert.not()) {
        // Check if user has on-line concert -> go to PreConcert / Concert
        checkOnlineConcertByUser()
    } else {
        if (uiState.navigateConcert) {
            uiState.documentId?.let {
                navToConcert(uiState.artistId, it)
            }
        }
        if (uiState.navigatePreConcert) {
            navToPreConcert(uiState.artistId)
        }
    }
}

@Composable
private fun InitialAuth(
    signIn: (AuthCredential) -> Unit,
    checkOnLineConcertsByUser: (String) -> Unit,
    currentUser: FirebaseUser?
) {
    /**
     * If user == null or anon -> check can this user be upgraded to normal auth user
     *                           linkAnonymousToAccount()
     *                           If yes -> split anon account to auth account
     *                           If no  -> auth in normal way with AuthorizationContent()
     * If user != null or anon -> check artist for on-line concert
     *                           If yes -> navigate to Concert
     *                           If no  -> navigate to PreConcert
     */

    if (currentUser == null || currentUser.isAnonymous) {
        Timber.i("currentUser == null || currentUser.isAnonymous")
        AuthorizationContent(signWithCredential = { signIn(it) })
    } else {
        Timber.i("currentUser != null || !currentUser.isAnonymous")
        checkOnLineConcertsByUser(currentUser.uid)
    }
}

@Composable
fun LoadAuth() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = StreetMusicTheme.colors.white)
        Text(
            text = stringResource(R.string.please_wait_auth),
            style = StreetMusicTheme.typography.errorPermission,
            color = StreetMusicTheme.colors.white
        )
    }
}

@Composable
fun ErrorAuth(value: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StreetMusicTheme.colors.artistGradientDown),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.error),
            color = StreetMusicTheme.colors.white
        )
        Text(text = ": $value", color = StreetMusicTheme.colors.white)
    }
}