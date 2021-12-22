package com.entin.streetmusic.ui.screens.authorizate.states

import androidx.compose.runtime.Composable
import com.entin.streetmusic.ui.screens.authorizate.InsideResponseAuthorize

@Composable
fun SuccessAuth(
    uiState: InsideResponseAuthorize,
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