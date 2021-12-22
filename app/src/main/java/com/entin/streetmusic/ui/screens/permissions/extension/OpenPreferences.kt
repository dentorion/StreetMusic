package com.entin.streetmusic.ui.screens.permissions.extension

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * Extension function to open App preferences
 * to change decision about gps-permissions
 */

fun Context.openSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}