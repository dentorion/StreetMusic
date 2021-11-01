package com.example.streetmusic.ui.cityconcerts

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streetmusic.R
import com.example.streetmusic.common.model.domain.ConcertDomain
import com.example.streetmusic.common.model.vmstate.CommonResponse
import com.example.streetmusic.ui.cityconcerts.components.ConcertsRecyclerView
import com.example.streetmusic.ui.cityconcerts.components.FinishedActiveButtons
import com.example.streetmusic.ui.cityconcerts.components.SortStyleAllConcertsByCity
import com.example.streetmusic.ui.preconcert.components.StyleMusicButtons
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun CityConcerts(
    viewModel: CityConcertsViewModel = hiltViewModel(),
    navToArtistPage: (String) -> Unit,
    navToMapObserve: () -> Unit
) {
    Log.i("MyMusic", "6.CityConcerts")
    Log.i("MyMusic", "6.CityConcerts. ViewModel hash : ${viewModel.hashCode()}")

    val userCity = viewModel.userCity
    val stateUi = viewModel.stateConcerts
    Log.i("MyMusic", "6.CityConcerts. stateUi : $stateUi")

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
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .background(StreetMusicTheme.colors.transparent)
                    .clickable {
                        navToMapObserve()
                    },
                painter = painterResource(id = R.drawable.ic_map_observe),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }

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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            /**
             * Background image
             */
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            /**
             * Buttons all
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding)
                    .padding(vertical = StreetMusicTheme.dimensions.allVerticalPadding)
                    .navigationBarsPadding(),
            ) {
                val isButtonEnabled = stateUi is CommonResponse.Success
                /**
                 * Styles buttons
                 */
                StyleMusicButtons(
                    onClick = { viewModel.switchStyle(it) },
                    actualChoice = viewModel.stateMusicTypeChoice,
                )
                /**
                 * All concerts button
                 */
                SortStyleAllConcertsByCity(
                    userCity = userCity,
                    onAllClick = { viewModel.switchAllStyles() },
                    actualAllStyle = viewModel.stateAllConcerts,
                    enabled = isButtonEnabled
                )
                /**
                 * Finished / Active buttons
                 */
                FinishedActiveButtons(viewModel, isButtonEnabled)
            }
        }
    }
}

@Composable
private fun InitialCityConcerts(action: () -> Unit) {
    action()
}

@Composable
private fun SuccessCityConcerts(
    state: List<ConcertDomain>,
    navToArtistPage: (String) -> Unit
) {
    Log.i("MyMusic", "6.CityConcertsContent.Success")
    Log.i("MyMusic", "6.CityConcertsContent.Success. List count: ${state.size}")
    ConcertsRecyclerView(
        data = state,
        navToArtistPage = navToArtistPage,
    )
}

@Composable
private fun ErrorCityConcerts() {
    Log.i("MyMusic", "6.CityConcertsContent.Error")
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
private fun LoadCityConcerts() {
    Log.i("MyMusic", "6.CityConcertsContent.Load")
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