package com.example.streetmusic2.ui.preconcert

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streetmusic2.R
import com.example.streetmusic2.common.model.viewmodelstate.CommonResponse
import com.example.streetmusic2.ui.artist.components.StatusOnline
import com.example.streetmusic2.ui.preconcert.components.*
import com.example.streetmusic2.ui.start.components.BackgroundImage
import com.example.streetmusic2.util.time.LocalTimeUtil
import com.example.streetmusic2.util.time.TimeUtil
import com.example.streetmusic2.util.user.LocalUserPref
import com.example.streetmusic2.util.user.UserSharedPreferences
import com.google.accompanist.insets.imePadding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun PreConcert(
    viewModel: PreConcertViewModel = hiltViewModel(),
    artistId: String,
    navToConcert: (String, String) -> Unit,
    navToMain: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BackgroundImage()

        Log.i("MyMusic", "9.PreConcert")
        val uiState = viewModel.statePreConcert

        when (uiState) {
            is CommonResponse.Error -> ErrorPreConcert(uiState.message)
            is CommonResponse.Initial -> PreConcertContent(
                viewModel = viewModel,
                artisId = artistId,
                navToMain = navToMain,
                userPref = LocalUserPref.current,
                uploadAvatar = { viewModel.uploadAvatar(it, artistId) },
            )
            is CommonResponse.Load -> LoadPreConcert()
            is CommonResponse.Success -> navToConcert(artistId, uiState.data)
        }
    }
}

@Composable
private fun PreConcertContent(
    viewModel: PreConcertViewModel,
    artisId: String,
    navToMain: () -> Unit,
    userPref: UserSharedPreferences,
    uploadAvatar: (Uri) -> Unit,
) {
    val scrollState = rememberScrollState()
    val timeUtil = LocalTimeUtil.current

    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
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
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {

                    },
                ) {
                    Image(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp),
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                    )
                }
            }
            /**
             * Avatar
             */
            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center
            ) {
                AvatarPreConcert(
                    artistAvatarUrl = viewModel.avatarUrl,
                    uploadAvatar = uploadAvatar,
                )
            }
            /**
             * Exit icon
             */
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        Firebase.auth.signOut()
                        navToMain()
                    },
                ) {
                    Image(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp),
                        painter = painterResource(id = R.drawable.ic_exit_user),
                        contentDescription = null,
                    )
                }

            }
        }

        /**
         * ON_Line / Off-line status
         */
        Box(Modifier.padding(20.dp)) {
            StatusOnline(false)
        }

        /**
         * City and Country
         */
        CityCountry()

        /**
         * Style Music buttons selector
         */
        StyleMusicButtons(
            onClick = {
                userPref.setStyleMusic(it.styleName)
                viewModel.musicType = it
            },
            actualChoice = viewModel.musicType,
        )

        /**
         * Text fields: bandName, address, description
         */
        TextFields(viewModel, userPref)

        /**
         * Start Concert button
         */
        StartButton(viewModel, userPref, artisId, timeUtil)
    }
}

@Composable
private fun StartButton(
    viewModel: PreConcertViewModel,
    userPref: UserSharedPreferences,
    artisId: String,
    timeUtil: TimeUtil
) {
    RunConcertButton(
        onClick = {
            startConcert(
                viewModel = viewModel,
                userPref = userPref,
                uId = artisId,
                timeUtil = timeUtil
            )
        },
        uID = artisId,
    )
}

@Composable
private fun TextFields(
    viewModel: PreConcertViewModel,
    userPref: UserSharedPreferences
) {
    BandNameTextField(
        caption = "Band Name",
        value = viewModel.bandName,
        onChange = {
            userPref.setNameGroup(it)
            viewModel.bandName = it
        })
    ConcertAddressTextField(
        caption = "Concert address",
        value = viewModel.address,
        onChange = {
            userPref.setAddress(it)
            viewModel.address = it
        })
    DescriptionTextField(
        caption = "Describe in few words",
        value = viewModel.description,
        onChange = {
            userPref.setDescription(it)
            viewModel.description = it
        })
}

@Composable
private fun LoadPreConcert() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Color.White)
        Text("Please, wait", color = Color.White)
    }
}

@Composable
private fun ErrorPreConcert(message: String) {
    Log.i("MyMusic", "9.PreConcert.Error")
    Log.e("MyMusic", message)
    DisableSelection {
        Text(
            text = message,
            color = Color.Black,
            maxLines = 1
        )
    }
}

/**
 * This should be done to run concert
 */
private fun startConcert(
    viewModel: PreConcertViewModel,
    userPref: UserSharedPreferences,
    uId: String,
    timeUtil: TimeUtil
) {
    viewModel.runConcert(
        latitude = userPref.getLatitude(),
        longitude = userPref.getLongitude(),
        country = userPref.getCountry(),
        city = userPref.getCity(),
        artistId = uId,
    )
    userPref.setTimeStop(timeUtil.getUnixNowPlusHour())
}

