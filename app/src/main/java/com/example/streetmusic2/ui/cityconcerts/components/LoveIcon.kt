package com.example.streetmusic2.ui.cityconcerts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.R
import com.example.streetmusic2.common.model.Concert

@Composable
fun LoveIcon(actionPressed: () -> Unit, concert: Concert) {
    var isFavourite: Boolean by remember { mutableStateOf(concert.isFavourite) }

    when (isFavourite) {
        true -> {
            TrueFavourite(actionPressed, onHeartChanged = { isFavourite = it })
        }
        false -> {
            FalseFavourite(actionPressed, onHeartChanged = { isFavourite = it })
        }
    }
}

@Composable
private fun FalseFavourite(
    onHeartPressed: () -> Unit,
    onHeartChanged: (Boolean) -> Unit
) {
    Image(
        modifier = Modifier
            .width(20.dp)
            .height(20.dp)
            .clickable {
                onHeartPressed()
                onHeartChanged(true)
            },
        painter = painterResource(id = R.drawable.ic_like_unchecked),
        contentDescription = null,
    )
}

@Composable
private fun TrueFavourite(
    onHeartPressed: () -> Unit,
    onHeartChanged: (Boolean) -> Unit,
) {
    Image(
        modifier = Modifier
            .width(20.dp)
            .height(20.dp)
            .clickable {
                onHeartPressed()
                onHeartChanged(false)
            },
        painter = painterResource(id = R.drawable.ic_like_checked),
        contentDescription = null,
    )
}