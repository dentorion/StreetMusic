package com.entin.streetmusic.ui.screens.preconcert.states

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.ui.screens.artist.components.StatusOnline
import com.entin.streetmusic.ui.screens.preconcert.PreConcertViewModel
import com.entin.streetmusic.ui.screens.preconcert.components.*

@ExperimentalCoilApi
@Composable
fun InitialPreConcert(
    viewModel: PreConcertViewModel,
    navToMain: () -> Unit,
    uploadAvatar: (Uri) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = StreetMusicTheme.dimensions.horizontalPaddingPre)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            /**
             * Star icon
             */
            StarButton(modifier = Modifier.weight(1f))

            /**
             * Avatar
             */
            AvatarPreConcert(
                modifier = Modifier.weight(2f),
                viewModel = viewModel,
                uploadAvatar = uploadAvatar,
            )

            /**
             * Exit icon
             */
            ExitButton(
                modifier = Modifier.weight(1f),
                navToMain = navToMain
            )
        }

        /**
         * ON_Line / Off-line status
         */
        StatusOnline(false)

        /**
         * City and Country
         */
        BandNameCityCountry(
            bandName = viewModel.bandName,
            city = viewModel.city,
            country = viewModel.country,
        )

        /**
         * Style Music buttons selector
         */
        StyleMusicButtons(
            onClick = { viewModel.setCurrentMusicType(it) },
            actualChoice = viewModel.musicType,
        )

        /**
         * Text fields: bandName, address, description
         */
        TextFields(viewModel)

        /**
         * Start Concert button
         */
        StartButton(viewModel)
    }
}