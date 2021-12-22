package com.entin.streetmusic.ui.screens.preconcert.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.entin.streetmusic.core.model.music.MusicType
import com.entin.streetmusic.core.theme.StreetMusicTheme

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
            .padding(vertical = StreetMusicTheme.dimensions.allVerticalPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
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
        BorderStroke(
            StreetMusicTheme.dimensions.borderStrokeActiveButton,
            StreetMusicTheme.colors.divider
        )
    } else {
        null
    }
    OutlinedButton(
        modifier = modifier.padding(horizontal = StreetMusicTheme.dimensions.paddingBetweenButtons),
        border = border,
        onClick = { onButtonClicked(musicType) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = StreetMusicTheme.colors.white,
            contentColor = StreetMusicTheme.colors.grayDark
        ),
        shape = StreetMusicTheme.shapes.medium,
    ) {
        Image(
            modifier = Modifier
                .padding(vertical = StreetMusicTheme.dimensions.buttonsVerticalPadding)
                .size(StreetMusicTheme.dimensions.musicStyleImageSize),
            painter = painterResource(id = musicType.icon),
            contentDescription = null,
        )
    }
}