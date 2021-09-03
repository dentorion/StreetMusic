package com.example.streetmusic2.ui.cityconcerts

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.streetmusic2.R
import com.example.streetmusic2.common.model.Concert
import com.example.streetmusic2.common.model.MyResponse
import com.example.streetmusic2.ui.cityconcerts.components.*
import com.example.streetmusic2.ui.permissions.components.ShowConcertsRecycler
import com.example.streetmusic2.util.constant.MusicStyle
import com.example.streetmusic2.util.userpref.LocalUserPref

@Composable
fun CityConcerts(
    viewModel: CityConcertsViewModel,
    navToMusicianPage: () -> Unit
) {
    val userCity = LocalUserPref.current.getCity() ?: ""

    CityConcertsContent(
        getAllConcerts = { viewModel.initialStart(userCity) },
        onHeartClick = { viewModel.clickHeart(it) },
        onStyleClick = { viewModel.switchStyleFunction(userCity, it) },
        onAllClick = { viewModel.switchAllFunction(userCity) },
        onFAClick = { viewModel.switchFAFunction(userCity, it) },
        actualChoiceStyle = viewModel.style,
        actualFAStyle = viewModel.switchFA,
        actualAllStyle = viewModel.switchAll,
        state = viewModel.stateConcerts,
        context = LocalContext.current,
        userCity = userCity,
        navToMusicianPage = navToMusicianPage
    )
}

@Composable
fun CityConcertsContent(
    getAllConcerts: () -> Unit,
    onHeartClick: (Int) -> Unit,
    onStyleClick: (String) -> Unit,
    onAllClick: () -> Unit,
    onFAClick: (Boolean) -> Unit,
    actualChoiceStyle: String,
    actualFAStyle: Boolean,
    actualAllStyle: Boolean,
    state: MyResponse<List<Concert>>,
    context: Context,
    userCity: String,
    navToMusicianPage: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        /**
         * Head part of Recycler List of concerts
         */
        Box(
            modifier = Modifier
                .weight(2.0f)
                .fillMaxSize()
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
                is MyResponse.Error -> Error()
                is MyResponse.Initial -> Initial(getAllConcerts)
                is MyResponse.Load -> Load()
                is MyResponse.Success -> Success(state, context, onHeartClick, navToMusicianPage)
            }
        }
        /**
         * Small pink line between Recycler and buttons
         */
        PinkDivider()

        /**
         * Buttons for getting concerts
         */
        Box(
            modifier = Modifier.weight(0.72f),
            contentAlignment = Alignment.Center
        ) {
            CityConcertsBackground()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 12.dp),
            ) {
                Row {
                    SortStyleButton(
                        style = MusicStyle.Rock(),
                        onStyleClicked = onStyleClick,
                        actual = actualChoiceStyle,
                        modifier = Modifier.weight(1f)
                    )
                    SortStyleButton(
                        style = MusicStyle.Classic(),
                        onStyleClicked = onStyleClick,
                        actual = actualChoiceStyle,
                        modifier = Modifier.weight(1f)
                    )
                    SortStyleButton(
                        style = MusicStyle.Dancing(),
                        onStyleClicked = onStyleClick,
                        actual = actualChoiceStyle,
                        modifier = Modifier.weight(1f)
                    )
                    SortStyleButton(
                        style = MusicStyle.Pop(),
                        onStyleClicked = onStyleClick,
                        actual = actualChoiceStyle,
                        modifier = Modifier.weight(1f)
                    )
                    SortStyleButton(
                        style = MusicStyle.Vocal(),
                        onStyleClicked = onStyleClick,
                        actual = actualChoiceStyle,
                        modifier = Modifier.weight(1f)
                    )
                }
                SortStyleAllConcertsByCity(userCity, onAllClick, actualAllStyle)
                Row {
                    SortStyleFinishedActualConcertsByCity(
                        onFAClick = onFAClick,
                        mode = false,
                        modifier = Modifier.weight(1f),
                        actualFAStyle = actualFAStyle
                    )
                    SortStyleFinishedActualConcertsByCity(
                        onFAClick = onFAClick,
                        mode = true,
                        modifier = Modifier.weight(1f),
                        actualFAStyle = actualFAStyle
                    )
                }
            }
        }
    }
}

@Composable
private fun Success(
    state: MyResponse.Success<List<Concert>>,
    context: Context,
    onHeartClick: (Int) -> Unit,
    navToMusicianPage: () -> Unit
) {
    ShowConcertsRecycler(
        data = state.data,
        context = context,
        onHeartClick = onHeartClick,
        navToMusicianPage = navToMusicianPage
    )
}

@Composable
private fun Initial(getAllConcerts: () -> Unit) {
    CircularProgressIndicator(modifier = Modifier.padding(bottom = 72.dp))
    getAllConcerts()
}

@Composable
private fun Load() {
    CircularProgressIndicator(modifier = Modifier.padding(bottom = 72.dp))
}

@Composable
private fun Error() {
    Text(
        text = stringResource(R.string.error),
        fontSize = 20.sp,
        maxLines = 1,
        modifier = Modifier.padding(bottom = 72.dp)
    )
}