package com.entin.streetmusic.data.time

import androidx.compose.runtime.staticCompositionLocalOf

val LocalTimeUtil = staticCompositionLocalOf<TimeUtil> {
    error("No TimeUtil found!")
}
