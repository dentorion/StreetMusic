package com.entin.streetmusic.ui.screens.cityconcerts.states

import androidx.compose.runtime.Composable
import com.entin.streetmusic.core.model.domain.ConcertDomain
import com.entin.streetmusic.ui.screens.cityconcerts.components.ConcertsRecyclerView
import timber.log.Timber

@Composable
fun SuccessCityConcerts(
    state: List<ConcertDomain>,
    navToArtistPage: (String) -> Unit
) {
    Timber.i("CityConcertsContent.Success")
    ConcertsRecyclerView(
        data = state,
        navToArtistPage = navToArtistPage,
    )
}
