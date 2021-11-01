package com.example.streetmusic.ui.preconcert.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.streetmusic.R
import com.example.streetmusic.common.theme.StreetMusicTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ExitButton(modifier: Modifier = Modifier, navToMain: () -> Unit) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = {
                Firebase.auth.signOut()
                navToMain()
            },
        ) {
            Image(
                modifier = Modifier.size(StreetMusicTheme.dimensions.starSizePre),
                painter = painterResource(id = R.drawable.ic_exit_user),
                contentDescription = null,
            )
        }

    }
}