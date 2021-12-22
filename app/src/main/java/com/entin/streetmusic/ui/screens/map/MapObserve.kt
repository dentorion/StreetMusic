package com.entin.streetmusic.ui.screens.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.entin.streetmusic.ui.screens.map.components.BottomMenu
import com.entin.streetmusic.ui.screens.map.components.MapViewContainer
import com.entin.streetmusic.data.user.LocalUserPref

@Composable
fun MapObserve(
    navToArtistPage: (String) -> Unit,
    navToCityConcerts: () -> Unit,
) {

    // Dialog State
    val openDialog = remember { mutableStateOf(false) }

    // Concerts that are on-line now from CityConcertsViewModel
    val concerts = LocalUserPref.current.getOnlineConcerts()

    // Read locations from viewModel and set them on the map
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Map container
            MapViewContainer(
                modifier = Modifier.weight(1f),
                concerts = concerts,
            )

            // Bottom menu
            BottomMenu(
                navToCityConcerts = navToCityConcerts,
                navToArtistPage = { navToArtistPage(it) },
                concerts = concerts,
                alertState = openDialog
            )
        }
    }
}