package com.entin.streetmusic.ui.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.entin.streetmusic.R
import com.entin.streetmusic.common.dialog.DialogWindow
import com.entin.streetmusic.common.dialog.components.DialogMapObserve
import com.entin.streetmusic.common.model.dialog.DialogType
import com.entin.streetmusic.common.model.domain.ConcertDomain
import com.entin.streetmusic.common.theme.StreetMusicTheme
import com.entin.streetmusic.ui.artist.components.IconCircle
import com.entin.streetmusic.ui.cityconcerts.CityConcertsViewModel
import com.entin.streetmusic.util.map.awaitMap
import com.entin.streetmusic.util.map.rememberMapViewWithLifecycle
import com.entin.streetmusic.util.user.LocalUserPref
import com.google.accompanist.insets.navigationBarsPadding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

@Composable
fun MapObserve(
    viewModel: CityConcertsViewModel = hiltViewModel(),
    navToArtistPage: (String) -> Unit,
    navToCityConcerts: () -> Unit,
) {
    Timber.i("Map ViewModel hash : " + viewModel.hashCode())

    val concerts = viewModel.onlineConcertsForMap
    /**
     * Read locations from viewModel and set them on the map
     */
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            /**
             * Map container
             */
            MapViewContainer(
                modifier = Modifier.weight(1f),
                concerts = viewModel.onlineConcertsForMap,
            )
            /**
             * Bottom menu
             */
            BottomMenu(
                navToCityConcerts = navToCityConcerts,
                navToArtistPage = { navToArtistPage(it) },
                concerts = concerts
            )
        }
    }
}

/**
 * Map container view
 */
@Composable
private fun MapViewContainer(
    concerts: List<ConcertDomain>,
    modifier: Modifier,
) {
    val mapView = rememberMapViewWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val userLocation = LatLng(
        LocalUserPref.current.getLatitude().toDouble(),
        LocalUserPref.current.getLongitude().toDouble()
    )

    Column(modifier = modifier.fillMaxSize()) {
        AndroidView({ mapView }) {
            coroutineScope.launch {
                val map = it.awaitMap()

                map.uiSettings.isZoomControlsEnabled = true

                if (!concerts.isNullOrEmpty()) {
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                concerts.first().latitude.toDouble(),
                                concerts.first().longitude.toDouble()
                            ), 15f
                        )
                    )
                } else {
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            userLocation, 15f
                        )
                    )
                }

                // Point marker on map
                concerts.forEach { concert ->
                    map.addMarker(
                        MarkerOptions()
                            .position(
                                LatLng(
                                    concert.latitude.toDouble(),
                                    concert.longitude.toDouble()
                                )
                            )
                            .title(concert.bandName)
                            .snippet(concert.address)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                    )
                }

                map.setOnMarkerClickListener {
                    Timber.i("Marker clicked: " + it.title + ", " + it.position + ", Можно показывать окошко с предложением открыть страниу Artist")
                    false
                }
            }
        }
    }
}

/**
 * Buttons
 */
@Composable
private fun BottomMenu(
    navToCityConcerts: () -> Unit,
    navToArtistPage: (String) -> Unit,
    concerts: List<ConcertDomain>,
) {
    val openDialog = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clip(StreetMusicTheme.shapes.small)
            .fillMaxWidth()
            .background(StreetMusicTheme.colors.mainFirst)
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .padding(all = StreetMusicTheme.dimensions.allHorizontalPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(
                navToCityConcerts = navToCityConcerts,
                modifier = Modifier.weight(1f)
            )

            Box(modifier = Modifier.padding(horizontal = StreetMusicTheme.dimensions.allHorizontalPadding)) {
                IconCircle(Color.Green)
            }

            ListButton(
                openDialog = openDialog,
                concerts = concerts,
                navToArtistPage = navToArtistPage,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ListButton(
    openDialog: MutableState<Boolean>,
    concerts: List<ConcertDomain>,
    navToArtistPage: (String) -> Unit,
    modifier: Modifier
) {
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
                    dialogState = openDialog,
                    dialogType = DialogType.MapObserve(),
                    content = {
                        DialogMapObserve(
                            listArtist = concerts,
                            navToMusicianPage = navToArtistPage
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun BackButton(
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

const val SPACER = " "