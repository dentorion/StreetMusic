package com.entin.streetmusic.ui.cityconcerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.entin.streetmusic.R
import com.entin.streetmusic.common.model.domain.ConcertDomain
import com.entin.streetmusic.common.model.vmstate.CommonResponse
import com.entin.streetmusic.common.theme.StreetMusicTheme
import com.entin.streetmusic.ui.cityconcerts.components.BannerMap
import com.entin.streetmusic.ui.cityconcerts.components.ConcertsRecyclerView
import com.entin.streetmusic.ui.cityconcerts.components.SortingButtons
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
    val stateUi = viewModel.stateConcerts

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
                is CommonResponse.Error -> ErrorCityConcerts()
                is CommonResponse.Initial -> InitialCityConcerts(action = { viewModel.getConcertsActualCity() })
                is CommonResponse.Load -> LoadCityConcerts()
                is CommonResponse.Success -> SuccessCityConcerts(
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

@Composable
private fun InitialCityConcerts(action: () -> Unit) {
    Timber.i("CityConcertsContent.Initial")

    Column(modifier = Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        CircularProgressIndicator(color = StreetMusicTheme.colors.white)
        DisableSelection {
            Text(
                text = stringResource(R.string.please_wait_perm),
                color = StreetMusicTheme.colors.white,
                style = StreetMusicTheme.typography.errorPermission
            )

            action()
        }
    }
}

@Composable
private fun SuccessCityConcerts(
    state: List<ConcertDomain>,
    navToArtistPage: (String) -> Unit
) {
    Timber.i("CityConcertsContent.Success")
    ConcertsRecyclerView(
        data = state,
        navToArtistPage = navToArtistPage,
    )
}

@Composable
private fun ErrorCityConcerts() {
    Timber.i("CityConcertsContent.Error")
    Column(modifier = Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        DisableSelection {
            Text(
                text = stringResource(R.string.error),
                color = StreetMusicTheme.colors.mainFirst,
            )
        }
    }
}

@Composable
fun LoadCityConcerts() {
    Timber.i("CityConcertsContent.Load")
    Column(modifier = Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        CircularProgressIndicator(color = StreetMusicTheme.colors.mainFirst)
        DisableSelection {
            Text(
                text = stringResource(R.string.please_wait_perm),
                color = StreetMusicTheme.colors.mainFirst,
                style = StreetMusicTheme.typography.errorPermission
            )
        }
    }
}