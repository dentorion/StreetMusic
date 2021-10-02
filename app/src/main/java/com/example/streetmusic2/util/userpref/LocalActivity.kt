package com.example.streetmusic2.util.userpref

import android.app.Activity
import androidx.compose.runtime.staticCompositionLocalOf

val LocalActivity = staticCompositionLocalOf<Activity> {
    error("No LocalActivity found!")
}