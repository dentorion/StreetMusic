package com.entin.streetmusic.ui.screens.concert.states

import androidx.compose.runtime.Composable

@Composable
fun SuccessConcert(navToPreConcert: (String) -> Unit, userId: String) {
    navToPreConcert(userId)
}