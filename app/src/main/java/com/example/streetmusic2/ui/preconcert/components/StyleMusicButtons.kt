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
import com.example.streetmusic2.common.model.music.MusicType

@Composable
fun StyleMusicButtons(
    onClick: (MusicType) -> Unit,
    actualChoice: MusicType,
) {
    var actualChoiceType: MusicType by remember { mutableStateOf(actualChoice) }
    actualChoiceType = actualChoice

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        SetIconStyleMusic(
            musicType = MusicType.Rock(),
            onButtonClicked = {
                actualChoiceType = MusicType.Rock()
                onClick(MusicType.Rock())
            },
            actual = actualChoiceType,
            modifier = Modifier.weight(1f),
        )
        SetIconStyleMusic(
            musicType = MusicType.Classic(),
            onButtonClicked = {
                actualChoiceType = MusicType.Classic()
                onClick(MusicType.Classic())
            },
            actual = actualChoiceType,
            modifier = Modifier.weight(1f),
        )
        SetIconStyleMusic(
            musicType = MusicType.Dancing(),
            onButtonClicked = {
                actualChoiceType = MusicType.Dancing()
                onClick(MusicType.Dancing())
            },
            actual = actualChoiceType,
            modifier = Modifier.weight(1f),
        )
        SetIconStyleMusic(
            musicType = MusicType.Pop(),
            onButtonClicked = {
                actualChoiceType = MusicType.Pop()
                onClick(MusicType.Pop())
            },
            actual = actualChoiceType,
            modifier = Modifier.weight(1f),
        )
        SetIconStyleMusic(
            musicType = MusicType.Vocal(),
            onButtonClicked = {
                actualChoiceType = MusicType.Vocal()
                onClick(MusicType.Vocal())
            },
            actual = actualChoiceType,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun SetIconStyleMusic(
    musicType: MusicType,
    onButtonClicked: (MusicType) -> Unit,
    actual: MusicType,
    modifier: Modifier,
) {
    val border = if (musicType.styleName == actual.styleName) {
        BorderStroke(4.dp, MaterialTheme.colors.primary)
    } else {
        null
    }
    OutlinedButton(
        modifier = modifier
            .height(55.dp)
            .padding(6.dp),
        border = border,
        onClick = { onButtonClicked(musicType) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Image(
            modifier = Modifier
                .width(20.dp)
                .height(20.dp),
            painter = painterResource(id = musicType.icon),
            contentDescription = null,
        )
    }
}