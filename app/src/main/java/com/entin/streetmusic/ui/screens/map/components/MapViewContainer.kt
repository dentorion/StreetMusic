package com.entin.streetmusic.ui.screens.map.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.entin.streetmusic.core.model.map.ShortConcertForMap
import com.entin.streetmusic.ui.screens.map.utils.awaitMap
import com.entin.streetmusic.ui.screens.map.utils.rememberMapViewWithLifecycle
import com.entin.streetmusic.data.user.LocalUserPref
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Map container view
 */
@Composable
fun MapViewContainer(
    concerts: List<ShortConcertForMap>,
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