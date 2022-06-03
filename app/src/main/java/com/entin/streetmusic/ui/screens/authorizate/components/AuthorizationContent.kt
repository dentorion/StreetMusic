package com.entin.streetmusic.ui.screens.authorizate.components

import android.app.Activity.RESULT_CANCELED
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
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
import com.entin.streetmusic.R
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import timber.log.Timber

@Composable
fun AuthorizationContent(
    signWithCredential: (AuthCredential) -> Unit
) {
    Timber.i("Authorize.AuthorizationContent")

    // Equivalent of onActivityResult
    val launcher: ManagedActivityResultLauncher<Intent, ActivityResult>?
    launcher = activityResult(signWithCredential)

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
            /**
             * Can show error Unresolved reference: default_web_client_id
             * But works ok.
             */
            val token = stringResource(id = R.string.default_web_client_id)

            // Logo
            AuthBigImage(modifier = Modifier.weight(1f))

            // Buttons
            AuthButtons(token, context, launcher)
        }
    }
}

@Composable
private fun activityResult(
    signWithCredential: (AuthCredential) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode != RESULT_CANCELED) {
            if (it.data != null) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)

                    signWithCredential(credential)

                } catch (e: ApiException) {
                    Timber.e(e.message)
                }
            } else {
                Timber.e("rememberLauncherForActivityResult. data == null.")
            }
        } else {
            Timber.e("rememberLauncherForActivityResult. resultCode. error.")
        }
    }
}