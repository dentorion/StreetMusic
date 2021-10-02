package com.example.streetmusic2.ui.preconcert

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
import com.example.streetmusic2.common.model.music.convertToMusicStyle
import com.example.streetmusic2.common.model.responce.CommonResponse
import com.example.streetmusic2.ui.artist.components.StatusOnline
import com.example.streetmusic2.ui.preconcert.components.*
import com.example.streetmusic2.ui.start.components.BackgroundImage
import com.example.streetmusic2.util.constant.HOUR_ONE_MLS
import com.example.streetmusic2.util.userpref.LocalUserPref
import com.example.streetmusic2.util.userpref.UserSharedPreferences
import com.google.accompanist.insets.imePadding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

@Composable
fun PreConcert(
    viewModel: PreConcertViewModel = hiltViewModel(),
    currentArtisId: String,
    navToConcert: (String) -> Unit,
    navToMain: () -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BackgroundImage()

        Log.i("MyMusic", "9.PreConcert")

        when (val uiState = viewModel.statePreConcert) {
            is CommonResponse.Error -> ErrorPreConcert(uiState.message)
            is CommonResponse.Initial -> InitialPreConcert(
                viewModel = viewModel,
                uId = currentArtisId,
                navToMain = navToMain
            )
            is CommonResponse.Load -> LoadPreConcert()
            is CommonResponse.Success -> SuccessPreConcert(
                navToConcert = navToConcert,
                uId = currentArtisId
            )
        }
    }
}

@Composable
private fun InitialPreConcert(viewModel: PreConcertViewModel, uId: String, navToMain: () -> Unit) {
    SetInitialValuesFromUserPref(viewModel)
    PreConcertContent(viewModel, uId, navToMain)
}

/**
 * First start - set userPref to default values from ViewModel
 * All other runs - set ViewModel fields from userPref values.
 */
@Composable
private fun SetInitialValuesFromUserPref(viewModel: PreConcertViewModel) {
    val userPref = LocalUserPref.current

    if (userPref.getStyleMusic().isNotEmpty()) {
        viewModel.musicStyle = convertToMusicStyle(userPref.getStyleMusic())
    } else {
        userPref.setStyleMusic(viewModel.musicStyle.styleName)
    }

    if (userPref.getNameGroup().isNotEmpty()) {
        viewModel.bandName = userPref.getNameGroup()
    } else {
        userPref.setNameGroup(viewModel.bandName)
    }

    if (userPref.getAddress().isNotEmpty()) {
        viewModel.address = userPref.getAddress()
    } else {
        userPref.setAddress(viewModel.address)
    }

    if (userPref.getDescription().isNotEmpty()) {
        viewModel.description = userPref.getDescription()
    } else {
        userPref.setDescription(viewModel.description)
    }
}

@Composable
private fun PreConcertContent(viewModel: PreConcertViewModel, uId: String, navToMain: () -> Unit) {
    val userPref = LocalUserPref.current
    val scrollState = rememberScrollState()

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
            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center
            ) {
                AvatarPreConcert(getAvatarUrl())
            }
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
        Box(Modifier.padding(20.dp)) {
            StatusOnline(false)
        }
        CityCountry()
        StyleMusicButtons(
            onClick = {
                userPref.setStyleMusic(it.styleName)
                viewModel.musicStyle = it
            },
            actualChoice = viewModel.musicStyle
        )
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
        RunConcertButton(
            onClick = {
                startConcertActions(viewModel, userPref, uId)
            },
            uID = uId,
        )
    }
}

/**
 * What should be done to run concert
 */
private fun startConcertActions(
    viewModel: PreConcertViewModel,
    userPref: UserSharedPreferences,
    uId: String
) {
    viewModel.runConcert(
        latitude = userPref.getLatitude(),
        longitude = userPref.getLongitude(),
        country = userPref.getCountry(),
        city = userPref.getCity(),
        artistId = uId,
        avatar = "",
    )
    userPref.setTimeStart(Date().time)
    userPref.setTimeStop(Date().time + HOUR_ONE_MLS)
}

/**
 * Загрузка аватарки по uId Artist
 */
fun getAvatarUrl(): String {
    return "https://image.shutterstock.com/image-illustration/casual-young-black-girl-purple-260nw-1806646354.jpg"
}

@Composable
private fun SuccessPreConcert(navToConcert: (String) -> Unit, uId: String) {
    navToConcert(uId)
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
fun ErrorPreConcert(message: String) {
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

