package com.example.streetmusic2.ui.preconcert.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.streetmusic2.R

@Composable
fun AvatarPreConcert(
    artistAvatarUrl: String,
    uploadAvatar: (Uri) -> Unit
    ) {
    Box {
        Avatar(artistAvatarUrl)
        AvatarUpload(uploadAvatar)
    }
}

@Composable
fun Avatar(artistAvatarUrl: String) {
    Image(
        painter = rememberImagePainter(
            data = artistAvatarUrl,
            builder = { transformations(CircleCropTransformation()) }
        ),
        contentDescription = null,
        modifier = Modifier
            .size(128.dp)
            .clip(CircleShape)
    )
}

@Composable
fun AvatarUpload(uploadAvatar: (Uri) -> Unit) {
    val selectImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            Log.i("MyMusic", "URI : $uri")
            // User press back without choosing avatar
            uri?.let { uploadAvatar(it) }
        }

    IconButton(
        onClick = {
            selectImageLauncher.launch("image/*")
        },
    ) {
        Image(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.ic_avatar_upload),
            contentDescription = null,
        )
    }
}