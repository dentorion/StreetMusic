package com.entin.streetmusic.ui.screens.permissions.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.entin.streetmusic.ui.common.ErrorMessageText
import com.entin.streetmusic.core.model.response.SMError

@Composable
fun ErrorPermissionsState(smError: SMError) {
    Column(modifier = Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterHorizontally) {
        ErrorMessageText(smError = smError)
    }
}