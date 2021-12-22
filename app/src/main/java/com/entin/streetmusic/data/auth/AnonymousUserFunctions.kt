package com.entin.streetmusic.data.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber

/**
 * Delete anonymous user on each onStop App
 */
fun deleteAnonymousUser() {
    val currentUser = Firebase.auth.currentUser
    val userIsAnonymous = currentUser?.isAnonymous ?: false
    if (userIsAnonymous) {
        currentUser?.delete()
        Firebase.auth.signOut()
        Timber.i("DELETED: userIsAnonymous: $userIsAnonymous, user: ${currentUser?.uid}")
    }
}

/**
 * Create anonymous user on each onRestart App and StartScreen composable
 */
fun createAnonymousUser() {
    val currentUser = Firebase.auth.currentUser
    if (currentUser == null) {
        Firebase.auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val newUser = Firebase.auth.currentUser
                    Timber.i("signInAnonymously:success. User: ${newUser?.uid}")
                } else {
                    Timber.i(task.exception, "signInAnonymously:failure")
                }
            }
    } else {
        Timber.i("NOT NULL user: ${currentUser.uid}, Is anon: ${currentUser.isAnonymous}")
    }
}
