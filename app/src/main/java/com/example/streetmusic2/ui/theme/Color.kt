package com.example.streetmusic2.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val White = Color(0xFFFFFFFF)
val Grey = Color(0xFFC8C8C8)
val DarkRed = Color(0xFFA51111)
val Green = Color(0xFF11A52F)
val Sky = Color(0xFF5B4ED5)
val Purple = Color(0xFFA158EF)
val DarkGrey = Color(0xFF4E4E4E)

val DarkColorPalette = darkColors(
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

val LightColorPalette = lightColors(
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