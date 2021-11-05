package com.entin.streetmusic.util.time

import androidx.compose.runtime.staticCompositionLocalOf

val LocalTimeUtil = staticCompositionLocalOf<TimeUtil> {
    error("No TimeUtil found!")
}
