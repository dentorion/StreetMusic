package com.example.streetmusic.ui.authorizate.components

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.streetmusic.R
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun AuthorizationContent(
    signWithCredential: (AuthCredential) -> Unit
) {
    Log.i("MyMusic", "7.Authorize.AuthorizationContent")

    // Equivalent of onActivityResult
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                signWithCredential(credential)
            } catch (e: ApiException) {
                Log.i("MyMusic", "Google sign in failed", e)
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        StreetMusicTheme.colors.grayLight,
                        StreetMusicTheme.colors.white
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding)
                .padding(bottom = StreetMusicTheme.dimensions.allBottomPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val context = LocalContext.current
            val token = stringResource(R.string.default_web_client_id)

            // Logo
            AuthBigImage(modifier = Modifier.weight(1f))

            // Buttons
            AuthButtons(token, context, launcher)
        }
    }
}