package com.example.streetmusic2.util.user

import androidx.compose.runtime.staticCompositionLocalOf

val LocalUserPref = staticCompositionLocalOf<UserSharedPreferences> {
    error("No UserSharedPreferences found!")
}
