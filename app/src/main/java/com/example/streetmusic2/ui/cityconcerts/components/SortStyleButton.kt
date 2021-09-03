package com.example.streetmusic2.ui.cityconcerts.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.R
import com.example.streetmusic2.util.constant.MusicStyle

@Composable
fun SortStyleButton(
    style: MusicStyle,
    onStyleClicked: (String) -> Unit,
    actual: String,
    modifier: Modifier
) {
    var nameStyleChoice = ""
    var iconStyleChoice = 0
    val border = if (style.styleName == actual) {
        BorderStroke(4.dp, MaterialTheme.colors.primary)
    } else {
        null
    }
    when (style) {
        is MusicStyle.Classic -> {
            nameStyleChoice = "classic"
            iconStyleChoice = R.drawable.ic_classic_music
        }
        is MusicStyle.Dancing -> {
            nameStyleChoice = "dancing"
            iconStyleChoice = R.drawable.ic_dancing_music
        }
        is MusicStyle.Pop -> {
            nameStyleChoice = "pop"
            iconStyleChoice = R.drawable.ic_pop_music
        }
        is MusicStyle.Rock -> {
            nameStyleChoice = "rock"
            iconStyleChoice = R.drawable.ic_rock_music
        }
        is MusicStyle.Vocal -> {
            nameStyleChoice = "vocal"
            iconStyleChoice = R.drawable.ic_vocal_music
        }
    }
    Content(nameStyleChoice, iconStyleChoice, border, modifier, onStyleClicked)
}

@Composable
private fun Content(
    nameStyleChoice: String,
    iconStyleChoice: Int,
    border: BorderStroke?,
    modifier: Modifier,
    onStyleClicked: (String) -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .height(55.dp)
            .padding(6.dp),
        border = border,
        onClick = { onStyleClicked(nameStyleChoice) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Image(
            modifier = Modifier
                .width(20.dp)
                .height(20.dp),
            painter = painterResource(id = iconStyleChoice),
            contentDescription = null,
        )
    }
}