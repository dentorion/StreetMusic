package com.example.streetmusic2.ui.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.R
import com.example.streetmusic2.common.model.Concert
import com.example.streetmusic2.common.model.MyResponse
import com.example.streetmusic2.ui.artist.components.*
import com.example.streetmusic2.ui.cityconcerts.components.CityConcertsBackground
import com.example.streetmusic2.ui.cityconcerts.components.PinkDivider

@Composable
fun PersonalPage(
    artistId: Long,
    viewModel: PersonalPageViewModel
) {
    PersonalPageContent(
        state = viewModel.stateConcerts,
        getConcert = { viewModel.getConcertsByArtisId(artistId = artistId) }
    )
}

@Composable
private fun PersonalPageContent(
    state: MyResponse<Pair<List<Concert>, List<Concert>>>,
    getConcert: () -> Unit
) {
    when (state) {
        is MyResponse.Success -> {
            val artistActiveConcert = state.data.first
            val artistExpiredConcert = state.data.second
            var source = artistExpiredConcert
            if (artistActiveConcert.isNotEmpty()) {
                source = artistActiveConcert
            }
            Success(
                artistActiveConcert = artistActiveConcert,
                artistExpiredConcert = artistExpiredConcert,
                artistOnline = artistActiveConcert.isNotEmpty(),
                artistName = source[0].name,
                artistAvatar = source[0].avatar,
                artistCity = source[0].city,
                artistCountry = source[0].country
            )
        }
        is MyResponse.Error -> {
            Error()
        }
        is MyResponse.Initial -> {
            Loading()
            getConcert()
        }
        is MyResponse.Load -> {
            Loading()
        }
    }
}

@Composable
fun Error() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gradientBottom)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.error), color = Color.White)
    }
}

@Composable
fun Success(
    artistActiveConcert: List<Concert>,
    artistExpiredConcert: List<Concert>,
    artistOnline: Boolean,
    artistName: String,
    artistAvatar: String,
    artistCity: String,
    artistCountry: String,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.weight(1.0f),
            contentAlignment = Alignment.Center
        ) {
            CityConcertsBackground()

            /**
             * HEADER
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Avatar(artistAvatar)
                StatusOnline(artistOnline)
                NameCityCountry(artistName, artistCity, artistCountry)
            }
        }
        PinkDivider()
        Box(
            modifier = Modifier
                .weight(2.0f)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.gradientUp),
                            colorResource(id = R.color.gradientBottom),
                        )
                    )
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                /**
                 * NOW PLAYING
                 */
                NowPlaying(artistActiveConcert)

                /**
                 * LAST PLAYING
                 */
                LastPlaying(artistExpiredConcert)
            }
        }
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gradientUp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}
