package com.example.streetmusic2.util.userpref

import androidx.compose.runtime.staticCompositionLocalOf

val LocalUserPref = staticCompositionLocalOf<UserSharedPreferences> {
    error("No UserSharedPreferences found!")
}
