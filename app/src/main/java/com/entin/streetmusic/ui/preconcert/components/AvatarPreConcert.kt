package com.entin.streetmusic.ui.preconcert.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.entin.streetmusic.R
import com.entin.streetmusic.common.theme.StreetMusicTheme
import com.entin.streetmusic.ui.preconcert.PreConcertViewModel

@ExperimentalCoilApi
@Composable
fun AvatarPreConcert(
    viewModel: PreConcertViewModel,
    uploadAvatar: (Uri) -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AvatarWithUploadIcon(
            artistAvatarUrl = viewModel.avatarUrl,
            uploadAvatar = uploadAvatar,
        )
    }
}

@ExperimentalCoilApi
@Composable
private fun AvatarWithUploadIcon(
    artistAvatarUrl: String,
    uploadAvatar: (Uri) -> Unit
) {
    Box {
        Avatar(artistAvatarUrl)
        AvatarUpload(uploadAvatar)
    }
}

@Composable
private fun AvatarUpload(uploadAvatar: (Uri) -> Unit) {
    val selectImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
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
                .size(StreetMusicTheme.dimensions.likeSizeCircle)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.ic_avatar_upload),
            contentDescription = null,
        )
    }
}

@coil.annotation.ExperimentalCoilApi
@Composable
fun Avatar(artistAvatarUrl: String) {
    Image(
        painter = rememberImagePainter(
            data = artistAvatarUrl,
            builder = { transformations(CircleCropTransformation()) }
        ),
        contentDescription = null,
        modifier = Modifier
            .size(StreetMusicTheme.dimensions.avatarSize)
            .clip(CircleShape)
    )
}