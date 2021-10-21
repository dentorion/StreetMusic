package com.example.streetmusic2.ui.cityconcerts

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streetmusic2.R
import com.example.streetmusic2.common.model.domain.ConcertDomain
import com.example.streetmusic2.common.model.music.MusicType
import com.example.streetmusic2.common.model.viewmodelstate.CommonResponse
import com.example.streetmusic2.ui.cityconcerts.components.SortStyleAllConcertsByCity
import com.example.streetmusic2.ui.cityconcerts.components.SortStyleFinishedActualConcertsByCity
import com.example.streetmusic2.ui.permissions.components.ConcertsRecyclerView
import com.example.streetmusic2.ui.preconcert.components.StyleMusicButtons
import com.example.streetmusic2.util.user.LocalUserPref
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun CityConcerts(
    viewModel: CityConcertsViewModel = hiltViewModel(),
    navToArtistPage: (String) -> Unit
) {
    Log.i("MyMusic", "6.CityConcerts")

    val userCity = LocalUserPref.current.getCity()
    CityConcertsContent(
        getAllConcerts = { viewModel.initialStart(userCity) },
        onHeartClick = { viewModel.clickHeart(it) },
        onStyleClick = { viewModel.switchStyleFunction(userCity, it) },
        onAllClick = { viewModel.switchAllFunction(userCity) },
        onFAClick = { viewModel.switchFAFunction(userCity, it) },
        stateMusicTypeChoice = viewModel.stateMusicTypeChoice,
        stateFinishedActiveConcerts = viewModel.stateFinishedActiveConcerts,
        stateAllConcerts = viewModel.stateAllConcerts,
        state = viewModel.stateConcerts,
        context = LocalContext.current,
        userCity = userCity,
        navToArtistPage = navToArtistPage
    )
}

@Composable
fun CityConcertsContent(
    getAllConcerts: () -> Unit,
    onHeartClick: (String) -> Unit,
    onStyleClick: (MusicType) -> Unit,
    onAllClick: () -> Unit,
    onFAClick: (Boolean) -> Unit,
    stateMusicTypeChoice: MusicType,
    stateFinishedActiveConcerts: Boolean,
    stateAllConcerts: Boolean,
    state: CommonResponse<List<ConcertDomain>>,
    context: Context,
    userCity: String,
    navToArtistPage: (String) -> Unit,
) {
    Log.i("MyMusic", "6.CityConcertsContent")

    Column {
        /**
         * Recycler List of concerts
         */
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .weight(1f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.secondary,
                            MaterialTheme.colors.secondaryVariant
                        )
                    )
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            when (state) {
                is CommonResponse.Error -> Error()
                is CommonResponse.Initial -> Initial(getAllConcerts)
                is CommonResponse.Load -> Load()
                is CommonResponse.Success -> Success(
                    state = state,
                    context = context,
                    onHeartClick = onHeartClick,
                    navToArtistPage = navToArtistPage
                )
            }
        }
        /**
         * Small pink line between Recycler and Buttons
         */
        Divider(color = Color.Magenta, thickness = 2.dp)

        /**
         * Buttons for getting concerts
         */
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp, bottom = 15.dp)
                    .navigationBarsPadding(),
            ) {
                val isButtonEnabled = state is CommonResponse.Success
                /**
                 * Styles buttons
                 */
                StyleMusicButtons(
                    onClick = onStyleClick,
                    actualChoice = stateMusicTypeChoice,
                )
                /**
                 * All styles button
                 */
                SortStyleAllConcertsByCity(
                    userCity = userCity,
                    onAllClick = onAllClick,
                    actualAllStyle = stateAllConcerts,
                    enabled = isButtonEnabled
                )
                /**
                 * Finished / Active buttons
                 */
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    SortStyleFinishedActualConcertsByCity(
                        onFAClick = onFAClick,
                        mode = false,
                        modifier = Modifier.weight(1f),
                        actualFAStyle = stateFinishedActiveConcerts,
                        enabled = isButtonEnabled
                    )
                    SortStyleFinishedActualConcertsByCity(
                        onFAClick = onFAClick,
                        mode = true,
                        modifier = Modifier.weight(1f),
                        actualFAStyle = stateFinishedActiveConcerts,
                        enabled = isButtonEnabled
                    )
                }
            }
        }
    }
}

@Composable
private fun Success(
    state: CommonResponse.Success<List<ConcertDomain>>,
    context: Context,
    onHeartClick: (String) -> Unit,
    navToArtistPage: (String) -> Unit
) {
    Log.i("MyMusic", "6.CityConcertsContent.Success")
    ConcertsRecyclerView(
        data = state.data,
        context = context,
        onHeartClick = onHeartClick,
        navToArtistPage = navToArtistPage,
    )
}

@Composable
private fun Initial(getAllConcerts: () -> Unit) {
    Log.i("MyMusic", "6.CityConcertsContent.Initial")
    CircularProgressIndicator(modifier = Modifier.padding(bottom = 72.dp))
    getAllConcerts()
}

@Composable
private fun Load() {
    Log.i("MyMusic", "6.CityConcertsContent.Load")
    CircularProgressIndicator(modifier = Modifier.padding(bottom = 72.dp))
}

@Composable
private fun Error() {
    Log.i("MyMusic", "6.CityConcertsContent.Error")
    Text(
        text = stringResource(R.string.error),
        fontSize = 20.sp,
        maxLines = 1,
        modifier = Modifier.padding(bottom = 72.dp)
    )
}