package com.example.streetmusic.common.dialog.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DialogPermissionsDefault(iconResource: Int) {
    Column {
        Text(
            modifier = Modifier.padding(all = 15.dp),
            text = "Application will show you street musicians according to your gps location. It's totally safe and secure.",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Image(
            painter = painterResource(iconResource),
            contentDescription = "",
            modifier = Modifier
                .padding(vertical = 24.dp)
                .padding(horizontal = 48.dp)
        )
    }
}