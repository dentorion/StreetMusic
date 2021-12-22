package com.entin.streetmusic.ui.screens.authorizate.states

import androidx.compose.runtime.Composable
import com.entin.streetmusic.ui.screens.authorizate.components.AuthorizationContent
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber

/**
 * If user == null or anon -> check can this user be upgraded to normal auth user
 *                           linkAnonymousToAccount()
 *                           If yes -> split anon account to auth account
 *                           If no  -> auth in normal way with AuthorizationContent()
 * If user != null or anon -> check artist for on-line concert
 *                           If yes -> navigate to Concert
 *                           If no  -> navigate to PreConcert
 */

@Composable
fun InitialAuth(
    signIn: (AuthCredential) -> Unit,
    checkOnLineConcertsByUser: (String) -> Unit,
    currentUser: FirebaseUser?
) {
    if (currentUser == null || currentUser.isAnonymous) {
        Timber.i("currentUser == null || currentUser.isAnonymous")
        AuthorizationContent(signWithCredential = { signIn(it) })
    } else {
        Timber.i("currentUser != null || !currentUser.isAnonymous")
        checkOnLineConcertsByUser(currentUser.uid)
    }
}