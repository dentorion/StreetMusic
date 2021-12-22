package com.entin.streetmusic.ui.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.core.model.response.SMError
import com.entin.streetmusic.core.theme.StreetMusicTheme

@Composable
fun ErrorMessageText(smError: SMError) {
    when (smError) {
        is SMError.ExceptionError -> ErrorMessageContent(smError.message)
        SMError.FailedStartConcert -> ErrorMessageContent(stringResource(id = R.string.error_pre_concert))
        SMError.FailedStopConcert -> ErrorMessageContent(stringResource(id = R.string.error_pre_concert))
        SMError.ImgUrlNull -> ErrorMessageContent(stringResource(id = R.string.avatar_img_null))
        SMError.LocationEmpty -> ErrorMessageContent(stringResource(id = R.string.need_permission))
        SMError.UserNull -> ErrorMessageContent(stringResource(id = R.string.auth_error))
    }
}

@Composable
private fun ErrorMessageContent(value: String) {
    Text(
        text = value,
        color = StreetMusicTheme.colors.white,
        style = StreetMusicTheme.typography.errorPermission,
    )
}