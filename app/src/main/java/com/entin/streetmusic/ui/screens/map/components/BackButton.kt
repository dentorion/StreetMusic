package com.entin.streetmusic.ui.screens.map.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.core.theme.StreetMusicTheme
import java.util.*

@Composable
fun BackButton(
    navToCityConcerts: () -> Unit,
    modifier: Modifier
) {
    OutlinedButton(
        modifier = modifier,
        onClick = {
            navToCityConcerts()
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(StreetMusicTheme.dimensions.backIconSize),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
            )
            Text(
                text = stringResource(id = R.string.back_to_list).uppercase(Locale.ENGLISH),
                color = StreetMusicTheme.colors.mainFirst,
                style = StreetMusicTheme.typography.buttonText
            )
        }
    }
}