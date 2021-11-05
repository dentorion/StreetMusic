package com.entin.streetmusic.util.user

import androidx.compose.runtime.staticCompositionLocalOf

val LocalUserPref = staticCompositionLocalOf<UserSession> {
    error("No UserSharedPreferences found!")
}
