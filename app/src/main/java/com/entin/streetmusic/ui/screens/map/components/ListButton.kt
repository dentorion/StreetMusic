package com.entin.streetmusic.ui.screens.map.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.core.dialog.DialogWindow
import com.entin.streetmusic.core.dialog.components.DialogMapObserve
import com.entin.streetmusic.core.model.dialog.MapObserveDialog
import com.entin.streetmusic.core.model.map.ShortConcertForMap
import com.entin.streetmusic.core.theme.StreetMusicTheme
import java.util.*

/**
 * List of on-line concerts button
 */
@Composable
fun ListButton(
    concerts: List<ShortConcertForMap>,
    navToArtistPage: (String) -> Unit,
    modifier: Modifier,
    alertState: MutableState<Boolean>
) {
    val openDialog = remember { mutableStateOf(alertState.value) }
    openDialog.value = alertState.value

    OutlinedButton(
        modifier = modifier,
        onClick = {
            openDialog.value = true
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.now_playing_map).uppercase(Locale.ENGLISH) + SPACER,
                color = StreetMusicTheme.colors.mainFirst,
                style = StreetMusicTheme.typography.buttonText
            )
            Text(
                text = concerts.size.toString().uppercase(Locale.ENGLISH),
                color = StreetMusicTheme.colors.mainFirst,
                style = StreetMusicTheme.typography.buttonText
            )
            Image(
                modifier = Modifier
                    .padding(start = StreetMusicTheme.dimensions.allHorizontalPadding)
                    .size((StreetMusicTheme.dimensions.backIconSize)),
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
            )

            if (openDialog.value) {
                DialogWindow(
                    dialogType = MapObserveDialog(),
                    content = {
                        DialogMapObserve(
                            listArtist = concerts,
                            navToMusicianPage = navToArtistPage,
                        )
                    },
                    openDialogState = openDialog,
                )
            }
        }
    }
}

const val SPACER = " "