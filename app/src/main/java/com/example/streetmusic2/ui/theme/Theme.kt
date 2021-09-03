package com.example.streetmusic2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorPalette = darkColors(
    primary = Purple,
    primaryVariant = Purple,

    secondary = White,
    secondaryVariant = Grey,

    background = White,
    surface = White,
    error = DarkRed,

    onPrimary = White,
    onSecondary = DarkGrey,

    onBackground = DarkGrey,
    onSurface = DarkGrey,
    onError = White
)

private val LightColorPalette = lightColors(
    primary = Purple,
    primaryVariant = Purple,

    secondary = White,
    secondaryVariant = Grey,

    background = White,
    surface = White,
    error = DarkRed,

    onPrimary = White,
    onSecondary = DarkGrey,

    onBackground = DarkGrey,
    onSurface = DarkGrey,
    onError = White

)

@Composable
fun StreetMusic2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes
    ) {
        CompositionLocalProvider(
            LocalRippleTheme provides MyRippleTheme,
            content = content
        )
    }
}

