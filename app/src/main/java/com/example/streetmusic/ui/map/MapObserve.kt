package com.example.streetmusic.ui.map

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streetmusic.R
import com.example.streetmusic.common.dialog.DialogWindow
import com.example.streetmusic.common.dialog.components.DialogMapObserve
import com.example.streetmusic.common.model.dialog.DialogType
import com.example.streetmusic.common.model.domain.ConcertDomain
import com.example.streetmusic.ui.artist.components.IconCircle
import com.example.streetmusic.ui.cityconcerts.CityConcertsViewModel
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.example.streetmusic.util.map.awaitMap
import com.example.streetmusic.util.map.rememberMapViewWithLifecycle
import com.example.streetmusic.util.user.LocalUserPref
import com.google.accompanist.insets.navigationBarsPadding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

@Composable
fun MapObserve(
    viewModel: CityConcertsViewModel = hiltViewModel(),
    navToArtistPage: (String) -> Unit,
    navToCityConcerts: () -> Unit,
) {
    /**
     * Read locations from viewModel and set them on the map
     */
    Log.i("MyMusic", "Map ViewModel hash : ${viewModel.hashCode()}")
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        MapScaffold(
            navToArtistPage = navToArtistPage,
            navToCityConcerts = navToCityConcerts,
            concerts = viewModel.onlineConcertsForMap
        )
    }
}

@Composable
private fun MapScaffold(
    navToArtistPage: (String) -> Unit,
    navToCityConcerts: () -> Unit,
    concerts: List<ConcertDomain>
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        scaffoldState = scaffoldState,
        content = {
            GoogleMapSnapshot(
                concerts = concerts
            )
        },
        bottomBar = {
            BottomMenuMapObserver(
                navToArtistPage = navToArtistPage,
                navToCityConcerts = navToCityConcerts,
                concerts = concerts,
            )
        }
    )
}

@Composable
private fun GoogleMapSnapshot(concerts: List<ConcertDomain>) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val mapView = rememberMapViewWithLifecycle()
        MapViewContainer(
            mapView = mapView,
            concerts = concerts
        )
    }
}

@Composable
private fun MapViewContainer(
    mapView: MapView,
    concerts: List<ConcertDomain>,
) {
    val coroutineScope = rememberCoroutineScope()
    val userLocation = LatLng(
        LocalUserPref.current.getLatitude().toDouble(),
        LocalUserPref.current.getLongitude().toDouble()
    )

    AndroidView({ mapView }) {
        coroutineScope.launch {
            val map = it.awaitMap()

            map.uiSettings.isZoomControlsEnabled = true

            if (!concerts.isNullOrEmpty()) {
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            concerts[0].latitude.toDouble(),
                            concerts[0].longitude.toDouble()
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
                Log.i(
                    "MyMusic",
                    "Marker clicked: ${it.title}, ${it.position}, Можно показывать окошко с предложением открыть страниу Artist"
                )
                false
            }
        }
    }
}

@Composable
private fun BottomMenuMapObserver(
    navToCityConcerts: () -> Unit,
    navToArtistPage: (String) -> Unit,
    concerts: List<ConcertDomain>,
) {
    val openDialog = remember { mutableStateOf(false) }

    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = StreetMusicTheme.colors.mainFirst,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(vertical = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {
                    navToCityConcerts()
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "back",
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = "Back to list",
                        color = StreetMusicTheme.colors.mainFirst,
                        fontSize = 14.sp
                    )
                }
            }

            OutlinedButton(
                onClick = {
                    openDialog.value = true
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconCircle(Color.Green)
                    Spacer(modifier = Modifier.padding(end = 5.dp))
                    Text(
                        text = "Now playing: ",
                        color = StreetMusicTheme.colors.mainFirst,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "${concerts.size}",
                        color = StreetMusicTheme.colors.mainFirst,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        modifier = Modifier.padding(start = 10.dp).size(14.dp),
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = "list",
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
    }
}