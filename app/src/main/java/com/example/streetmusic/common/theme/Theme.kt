package com.example.streetmusic.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.streetmusic.common.theme.components.*

/**
 * Application Theme Composable
 */
@Composable
fun StreetMusicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    /**
     * Colors
     */
    val colors: StreetMusicColors = when (darkTheme) {
        true -> baseColorLightPalette
        false -> baseColorDarkPalette
    }

    /**
     * Typography
     */
    val typography: StreetMusicTypography = typographyPalette

    /**
     * Shapes
     */
    val shapes: StreetMusicShapes = shapesPalette

    /**
     * Dimensions
     */
    val dimensions: StreetMusicDimensions = dimensionsPalette

    /**
     * Providing created colors, typography, shapes and dimensions
     */
    CompositionLocalProvider(
        LocalStreetMusicColors provides colors,
        LocalStreetMusicTypography provides typography,
        LocalStreetMusicShapes provides shapes,
        LocalStreetMusicDimensions provides dimensions,
        content = content
    )
}