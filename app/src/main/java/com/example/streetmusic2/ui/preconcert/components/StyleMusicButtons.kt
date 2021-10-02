package com.example.streetmusic2.ui.preconcert.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.streetmusic2.common.model.music.MusicStyle

@Composable
fun StyleMusicButtons(
    onClick: (MusicStyle) -> Unit,
    actualChoice: MusicStyle
) {
    var actualChoiceStyle: MusicStyle by remember { mutableStateOf(actualChoice) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        SetIconStyleMusic(
            musicStyle = MusicStyle.Rock(),
            onButtonClicked = {
                actualChoiceStyle = MusicStyle.Rock()
                onClick(MusicStyle.Rock())
            },
            actual = actualChoiceStyle,
            modifier = Modifier.weight(1f),
            enabled = true
        )
        SetIconStyleMusic(
            musicStyle = MusicStyle.Classic(),
            onButtonClicked = {
                actualChoiceStyle = MusicStyle.Classic()
                onClick(MusicStyle.Classic())
            },
            actual = actualChoiceStyle,
            modifier = Modifier.weight(1f),
            enabled = true
        )
        SetIconStyleMusic(
            musicStyle = MusicStyle.Dancing(),
            onButtonClicked = {
                actualChoiceStyle = MusicStyle.Dancing()
                onClick(MusicStyle.Dancing())
            },
            actual = actualChoiceStyle,
            modifier = Modifier.weight(1f),
            enabled = true
        )
        SetIconStyleMusic(
            musicStyle = MusicStyle.Pop(),
            onButtonClicked = {
                actualChoiceStyle = MusicStyle.Pop()
                onClick(MusicStyle.Pop())
            },
            actual = actualChoiceStyle,
            modifier = Modifier.weight(1f),
            enabled = true
        )
        SetIconStyleMusic(
            musicStyle = MusicStyle.Vocal(),
            onButtonClicked = {
                actualChoiceStyle = MusicStyle.Vocal()
                onClick(MusicStyle.Vocal())
            },
            actual = actualChoiceStyle,
            modifier = Modifier.weight(1f),
            enabled = true
        )
    }
}

@Composable
private fun SetIconStyleMusic(
    musicStyle: MusicStyle,
    onButtonClicked: (MusicStyle) -> Unit,
    actual: MusicStyle,
    modifier: Modifier,
    enabled: Boolean
) {
    val border = if (musicStyle.styleName == actual.styleName) {
        BorderStroke(4.dp, MaterialTheme.colors.primary)
    } else {
        null
    }
    OutlinedButton(
        modifier = modifier
            .height(55.dp)
            .padding(6.dp),
        border = border,
        onClick = { onButtonClicked(musicStyle) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ),
        shape = MaterialTheme.shapes.medium,
        enabled = enabled
    ) {
        Image(
            modifier = Modifier
                .width(20.dp)
                .height(20.dp),
            painter = painterResource(id = musicStyle.icon),
            contentDescription = null,
        )
    }
}