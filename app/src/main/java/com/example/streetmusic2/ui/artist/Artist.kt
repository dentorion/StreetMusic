package com.example.streetmusic2.ui.artist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streetmusic2.R
import com.example.streetmusic2.common.model.concert.ConcertDomain
import com.example.streetmusic2.common.model.responce.CommonResponse
import com.example.streetmusic2.ui.artist.components.*
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Artist(
    artistId: String,
    viewModel: ArtistViewModel = hiltViewModel()
) {
    Log.i("MyMusic", "2.Artist")

    ArtistContent(
        state = viewModel.stateConcerts,
        getConcert = { viewModel.getConcertsByArtisId(artistId = artistId) }
    )
}

@Composable
private fun ArtistContent(
    state: CommonResponse<Pair<List<ConcertDomain>, List<ConcertDomain>>>,
    getConcert: () -> Unit
) {
    Log.i("MyMusic", "2.ArtistContent")

    when (state) {
        is CommonResponse.Success -> {
            Log.i("MyMusic", "2.ArtistContent.Success")

            val artistActiveConcert = state.data.first
            val artistExpiredConcert = state.data.second

            /**
             * Decide where band name, avatar, city, country should be taken from
             * Active concert or last Finished concert
             */
            val actualSourceOfArtistData = if (artistActiveConcert.isNotEmpty()) {
                artistActiveConcert
            } else {
                artistExpiredConcert
            }
            Success(
                artistActiveConcertDomain = artistActiveConcert,
                artistExpiredConcertDomain = artistExpiredConcert,
                artistOnline = artistActiveConcert.isNotEmpty(),
                artistName = actualSourceOfArtistData[0].name,
                artistAvatar = actualSourceOfArtistData[0].avatar,
                artistCity = actualSourceOfArtistData[0].city,
                artistCountry = actualSourceOfArtistData[0].country
            )
        }
        is CommonResponse.Error -> {
            Log.i("MyMusic", "2.ArtistContent.Error")

            Error()
        }
        is CommonResponse.Initial -> {
            Log.i("MyMusic", "2.ArtistContent.Initial")

            Loading()
            getConcert()
        }
        is CommonResponse.Load -> {
            Log.i("MyMusic", "2.ArtistContent.Load")

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
    artistActiveConcertDomain: List<ConcertDomain>,
    artistExpiredConcertDomain: List<ConcertDomain>,
    artistOnline: Boolean,
    artistName: String,
    artistAvatar: String,
    artistCity: String,
    artistCountry: String,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        /**
         * HEADER
         */
        Header(artistAvatar, artistOnline, artistName, artistCity, artistCountry)

        /**
         * Small pink line between Recycler and buttons
         */
        Divider(color = Color.Magenta, thickness = 2.dp)

        /**
         * List of concerts: actual and finished
         */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.gradientUp),
                            colorResource(id = R.color.gradientBottom),
                        )
                    )
                )
                .padding(15.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            ConcertsRecyclerView(artistActiveConcertDomain, artistExpiredConcertDomain)
        }
    }
}

@Composable
private fun ConcertsRecyclerView(
    artistActiveConcertDomain: List<ConcertDomain>,
    artistExpiredConcertDomain: List<ConcertDomain>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(horizontal = 10.dp)
    ) {
        /**
         * On-line
         */
        item {
            HeaderOfList(online = true, caption = "NOW PLAYING")
        }
        if (artistActiveConcertDomain.isEmpty()) {
            item {
                NoConcerts()
            }
        } else {
            items(items = artistActiveConcertDomain) { concert ->
                ConcertItem(concert)
                DescriptionOnlineConcert(concertDomain = concert, color = Color.Green)
            }
        }

        /**
         * Off-line
         */
        item {
            HeaderOfList(online = false, caption = "LAST PLAYING")
        }
        if (artistExpiredConcertDomain.isEmpty()) {
            item {
                NoConcerts()
            }
        } else {
            items(items = artistExpiredConcertDomain) { concert ->
                ConcertItem(concert)
                DescriptionOnlineConcert(concertDomain = concert, color = Color.Red)
            }
        }
    }
}

@Composable
private fun Header(
    artistAvatar: String,
    artistOnline: Boolean,
    artistName: String,
    artistCity: String,
    artistCountry: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 10.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Avatar(artistAvatar)
            Box(modifier = Modifier.padding(vertical = 20.dp)) {
                StatusOnline(artistOnline)
            }
            BandNameCityCountry(artistName, artistCity, artistCountry)
        }
    }
}

@Composable
private fun DescriptionOnlineConcert(concertDomain: ConcertDomain, color: Color) {
    Row(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(start = 15.dp)
    ) {
        Column {
            Divider(
                color = color, modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 10.dp)
        ) {
            Row {
                Text(
                    text =
                    SimpleDateFormat(
                        "dd.MM.yy",
                        Locale.getDefault()
                    ).format(concertDomain.timeStart)
                            + " / " +
                            SimpleDateFormat(
                                "HH:mm",
                                Locale.getDefault()
                            ).format(concertDomain.timeStart)
                            + " - " +
                            SimpleDateFormat(
                                "HH:mm",
                                Locale.getDefault()
                            ).format(concertDomain.timeStop),
                    color = Color.White,
                    fontSize = 14.sp,
                )
            }
            if (concertDomain.address.isNotEmpty()) {
                Text(
                    text = concertDomain.address,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
            if (concertDomain.description.isNotEmpty()) {
                Text(
                    text = concertDomain.description,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
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
