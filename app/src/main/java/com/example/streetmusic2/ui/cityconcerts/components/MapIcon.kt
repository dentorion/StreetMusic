package com.example.streetmusic2.ui.cityconcerts.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.streetmusic2.R
import com.example.streetmusic2.common.model.Concert


@Composable
fun MapIcon(
    context: Context,
    concert: Concert
) {
    Image(
        modifier = Modifier
            .width(35.dp)
            .height(35.dp)
            .clickable {
                // Creates an Intent that will load a map of Location
                // Create a Uri from an intent string. Use the result to create an Intent.
                val gmmIntentUri = Uri.parse("google.navigation:q=${concert.latitude},${concert.longitude}")

                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps")

                // Attempt to start an activity that can handle the Intent
                startActivity(context, mapIntent, null)
            },
        painter = painterResource(id = R.drawable.ic_map),
        contentDescription = null,
    )
}