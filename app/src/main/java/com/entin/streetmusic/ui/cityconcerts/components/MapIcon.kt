package com.entin.streetmusic.ui.cityconcerts.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat.startActivity
import com.entin.streetmusic.R

@Composable
fun MapIcon(modifier: Modifier = Modifier, latitude: String, longitude: String) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        MapIconContent(
            latitude = latitude,
            longitude = longitude,
        )
    }
}

/**
 * Creates an Intent that will load a map of Location
 */
@Composable
private fun MapIconContent(
    context: Context = LocalContext.current,
    latitude: String,
    longitude: String,
) {
    Box(
        modifier = Modifier.clickable {
            // Create a Uri from an intent string. Use the result to create an Intent.
            val gmmIntentUri = Uri.parse("google.navigation:q=${latitude},${longitude}")

            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps")

            // Attempt to start an activity that can handle the Intent
            startActivity(context, mapIntent, null)
        },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_map),
            contentDescription = null,
        )
    }
}