package com.example.streetmusic2.ui.concert

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streetmusic2.R
import com.example.streetmusic2.common.model.viewmodelstate.CommonResponse
import com.example.streetmusic2.ui.artist.components.Avatar
import com.example.streetmusic2.ui.artist.components.StatusOnline
import com.example.streetmusic2.ui.concert.components.StopConcertButton
import com.example.streetmusic2.ui.preconcert.components.CityCountry
import com.example.streetmusic2.ui.start.components.BackgroundImage
import com.example.streetmusic2.util.time.LocalTimeUtil
import com.example.streetmusic2.util.user.LocalUserPref
import com.google.accompanist.insets.imePadding

@Composable
fun Concert(
    viewModel: ConcertViewModel = hiltViewModel(),
    documentId: String,
    navToPreConcert: (String) -> Unit,
    userId: String
) {

    Log.i("MyMusic","documentId: $documentId")
    Log.i("MyMusic","userId: $userId")

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BackgroundImage()

        when (val uiState = viewModel.stateConcert) {
            is CommonResponse.Error -> ErrorConcert(uiState.message)
            is CommonResponse.Initial -> InitialConcert(
                stopConcert = { viewModel.stopConcert(documentId) },
            )
            is CommonResponse.Load -> LoadConcert()
            is CommonResponse.Success -> { navToPreConcert(userId) }
        }
    }
}

@Composable
fun LoadConcert() {
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
fun InitialConcert(
    stopConcert: () -> Unit
) {
    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Avatar(LocalUserPref.current.getAvatar())
            }
        }
        Box(Modifier.padding(20.dp)) {
            StatusOnline(true)
        }
        CityCountry()
        StarRating()
        StopConcertButton(onClick = stopConcert)
        Text(
            text = "AUTO STOP: ${getEndTimeString()}",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun getEndTimeString(): String {
    val userPref = LocalUserPref.current
    val timeUtil = LocalTimeUtil.current
    return timeUtil.getStringConcertTime(userPref.getTimeStop())
}

@Composable
private fun StarRating() {
    Box(Modifier.padding(35.dp)) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Min),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp), color = Color.White
            )
            Text(text = "BEGINNER", color = Color.White, fontSize = 12.sp)
        }
    }
}

@Composable
fun ErrorConcert(message: String) {
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
