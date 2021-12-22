package com.entin.streetmusic.ui.screens.cityconcerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.hilt.navigation.compose.hiltViewModel
import com.entin.streetmusic.core.model.response.SMResponse
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.ui.screens.cityconcerts.components.BannerMap
import com.entin.streetmusic.ui.screens.cityconcerts.components.SortingButtons
import com.entin.streetmusic.ui.screens.cityconcerts.states.ErrorCityConcerts
import com.entin.streetmusic.ui.screens.cityconcerts.states.InitialCityConcerts
import com.entin.streetmusic.ui.screens.cityconcerts.states.LoadCityConcerts
import com.entin.streetmusic.ui.screens.cityconcerts.states.SuccessCityConcerts
import timber.log.Timber

@Composable
fun CityConcerts(
    viewModel: CityConcertsViewModel = hiltViewModel(),
    navToArtistPage: (String) -> Unit,
    navToMapObserve: () -> Unit
) {
    Timber.i("CityConcerts")
    Timber.i("CityConcerts. ViewModel hash : " + viewModel.hashCode())

    val userCity = viewModel.userCity
    val stateUi = viewModel.uiStateCityConcerts

    Column(
        modifier = Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    StreetMusicTheme.colors.grayLight,
                    StreetMusicTheme.colors.white
                )
            )
        )
    ) {
        /**
         * Banner map
         */
        BannerMap(navToMapObserve)

        /**
         * Content Recycler view
         */
        Box(
            modifier = Modifier
                .padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding)
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            when (stateUi) {
                is SMResponse.ErrorResponse -> ErrorCityConcerts(stateUi.SMError)
                is SMResponse.InitialResponse -> InitialCityConcerts(
                    getConcertsActualCity = { viewModel.getConcertsActualCity() }
                )
                is SMResponse.LoadResponse -> LoadCityConcerts()
                is SMResponse.SuccessResponse -> SuccessCityConcerts(
                    state = stateUi.data,
                    navToArtistPage = navToArtistPage
                )
            }
        }
        /**
         * Small pink line between Recycler and Buttons
         */
        Divider(
            color = StreetMusicTheme.colors.divider,
            thickness = StreetMusicTheme.dimensions.dividerThickness
        )

        /**
         * Buttons for getting concerts
         */
        SortingButtons(stateUi, viewModel, userCity)
    }
}