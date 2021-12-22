package com.entin.streetmusic.ui.screens.cityconcerts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.entin.streetmusic.R
import com.entin.streetmusic.core.model.domain.ConcertDomain
import com.entin.streetmusic.core.model.response.SMResponse
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.ui.screens.cityconcerts.CityConcertsViewModel
import com.entin.streetmusic.ui.screens.preconcert.components.StyleMusicButtons
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun SortingButtons(
    stateUi: SMResponse<List<ConcertDomain>>,
    viewModel: CityConcertsViewModel,
    userCity: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        /**
         * Background image
         */
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
            val isButtonEnabled = stateUi is SMResponse.SuccessResponse
            /**
             * Styles buttons
             */
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
            /**
             * Finished / Active buttons
             */
            FinishedActiveButtons(viewModel, isButtonEnabled)
        }
    }
}