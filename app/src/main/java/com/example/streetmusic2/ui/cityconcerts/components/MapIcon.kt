package com.example.streetmusic2.ui.cityconcerts.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.streetmusic2.R
import com.example.streetmusic2.common.model.concert.ConcertDomain


@Composable
fun MapIcon(
    context: Context,
    concertDomain: ConcertDomain
) {
    Box(
        modifier = Modifier.clickable {
            /**
             * Creates an Intent that will load a map of Location
             */
            // Create a Uri from an intent string. Use the result to create an Intent.
            val gmmIntentUri =
                Uri.parse("google.navigation:q=${concertDomain.latitude},${concertDomain.longitude}")

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
            modifier = Modifier.size(35.dp),
            painter = painterResource(id = R.drawable.ic_map),
            contentDescription = null,
        )
    }
}