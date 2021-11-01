package com.example.streetmusic.ui.authorizate

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

fun googleSignIn(
    token: String,
    context: Context,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    val gso =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail()
            .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    googleSignInClient.revokeAccess()
    launcher.launch(googleSignInClient.signInIntent)
}