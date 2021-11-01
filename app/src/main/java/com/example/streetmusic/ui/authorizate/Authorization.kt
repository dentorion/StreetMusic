package com.example.streetmusic.ui.authorizate

import android.util.Log
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
import com.example.streetmusic.R
import com.example.streetmusic.ui.authorizate.components.AuthorizationContent
import com.example.streetmusic.ui.authorizate.uistate.AuthorizeResponse
import com.example.streetmusic.ui.start.components.BackgroundImage
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.example.streetmusic.util.user.LocalUserPref
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun Authorize(
    viewModel: AuthorizeViewModel = hiltViewModel(),
    navToPreConcert: (String) -> Unit,
    navToConcert: (artistId: String, documentId: String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BackgroundImage()

        Log.i("MyMusic", "7.Authorize")
        val state = viewModel.authorizeState

        when (state) {
            is AuthorizeResponse.Error -> {
                Log.i("MyMusic", "7.Authorize.E")
                ErrorAuth(state.value)
            }

            is AuthorizeResponse.Initial -> {
                Log.i("MyMusic", "7.Authorize.I")
                InitialAuth(
                    currentUser = Firebase.auth.currentUser,
                    signWithCredential = { viewModel.signWithCredential(it) },
                    checkOnLineConcertsByUser = { viewModel.checkOnlineConcertByUser(it) },
                )
            }

            is AuthorizeResponse.Load -> {
                Log.i("MyMusic", "7.Authorize.L")
                LoadAuth()
            }

            is AuthorizeResponse.Success -> {
                Log.i("MyMusic", "7.Authorize.S")
                SuccessAuth(state, viewModel)
            }

            is AuthorizeResponse.Navigate -> {
                Log.i("MyMusic", "7.Authorize.N")
                NavigateAuth(state, navToPreConcert, navToConcert)
            }
        }
    }
}

@Composable
private fun NavigateAuth(
    state: AuthorizeResponse.Navigate,
    navToPreConcert: (String) -> Unit,
    navToConcert: (artistId: String, documentId: String) -> Unit,
) {
    if (state.documentId.isNullOrEmpty()) {
        navToPreConcert(state.artistId)
    } else {
        navToConcert(state.artistId, state.documentId)
    }
}

@Composable
private fun SuccessAuth(
    state: AuthorizeResponse.Success,
    viewModel: AuthorizeViewModel
) {
    val userPref = LocalUserPref.current
    state.user?.let { user ->
        // Save user Id to userPref
        userPref.setId(user.uid)
        // Check if user has on-line concert -> go to PreConcert / Concert
        viewModel.checkOnlineConcertByUser(artistId = user.uid)
    }
}

@Composable
private fun InitialAuth(
    signWithCredential: (AuthCredential) -> Unit,
    checkOnLineConcertsByUser: (String) -> Unit,
    currentUser: FirebaseUser?
) {
    /**
     *  If Artist is not authorized -> Show authorization composable
     *  If Artist is authorized     -> Check for on-line concert:
     *  - Actual concert is -> navigate to Concert
     *  - No actual concert -> navigate to PreConcert
     */
    if (currentUser != null) {
        checkOnLineConcertsByUser(currentUser.uid)
    } else {
        AuthorizationContent(signWithCredential = { signWithCredential(it) })
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
        Text(text = value, color = StreetMusicTheme.colors.white)
    }
}