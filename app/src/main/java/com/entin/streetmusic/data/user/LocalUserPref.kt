package com.entin.streetmusic.data.user

import androidx.compose.runtime.staticCompositionLocalOf

val LocalUserPref = staticCompositionLocalOf<UserSession> {
    error("No UserSharedPreferences found!")
}
