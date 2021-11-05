package com.entin.streetmusic.ui.authorizate.components

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.entin.streetmusic.R
import com.entin.streetmusic.ui.authorizate.google.googleSignIn
import com.entin.streetmusic.common.theme.StreetMusicTheme
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun AuthButtons(
    token: String,
    context: Context,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = StreetMusicTheme.dimensions.allBottomPadding),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {
            SignLabel()
            GoogleSignButton(token, context, launcher)
        }
    }
}

@Composable
private fun GoogleSignButton(
    token: String,
    context: Context,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    OutlinedButton(
        border = ButtonDefaults.outlinedBorder.copy(
            width = StreetMusicTheme.dimensions.authBorderThickness
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = StreetMusicTheme.dimensions.buttonsVerticalPadding),
        onClick = {
            googleSignIn(token, context, launcher)
        },
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        tint = Color.Unspecified,
                        painter = painterResource(id = R.drawable.googleg_standard_color_18),
                        contentDescription = null,
                    )
                    Text(
                        style = StreetMusicTheme.typography.buttonText,
                        color = StreetMusicTheme.colors.gray,
                        text = stringResource(id = R.string.google_name_button_label)
                    )
                    Text(
                        color = StreetMusicTheme.colors.white,
                        text = ""
                    )
                }
            )
        }
    )
}

@Composable
private fun SignLabel() {
    Text(
        modifier = Modifier.padding(bottom = StreetMusicTheme.dimensions.allVerticalPadding),
        text = stringResource(id = R.string.sign_in_with),
        style = StreetMusicTheme.typography.signIn
    )
}