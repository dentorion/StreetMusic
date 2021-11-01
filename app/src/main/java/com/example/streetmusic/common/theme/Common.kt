package com.example.streetmusic.common.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Application style colors
 */

data class StreetMusicColors(
    val mainFirst: Color,
    val mainSecond: Color,
    val green: Color,
    val red: Color,
    val white: Color,
    val grayLight: Color,
    val gray: Color,
    val grayDark: Color,
    val divider: Color,
    val backgroundOnline: Color,
    val artistGradientUp: Color,
    val artistGradientDown: Color,
    val transparent: Color,
    val concertCardBackground: Color,
)

/**
 * Application style typography
 */
data class StreetMusicTypography(
    val buttonText: TextStyle,
    val bandName: TextStyle,
    val cityCountry: TextStyle,
    val onlineOffline: TextStyle,
    val startDate: TextStyle,
    val startDateLabel: TextStyle,
    val description: TextStyle,
    val dateCreateConcert: TextStyle,
    val emptyList: TextStyle,
    val errorPermission: TextStyle,
    val signIn: TextStyle,
    val labelField: TextStyle,
    val autoStop: TextStyle,
)

/**
 * Application style shapes
 */
data class StreetMusicShapes(
    val small: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val large: RoundedCornerShape,
)

/**
 * Application style dimensions
 */
data class StreetMusicDimensions(
    val allHorizontalPadding: Dp,
    val allBottomPadding: Dp,
    val buttonsVerticalPadding: Dp,
    val allVerticalPadding: Dp,
    val dividerThickness: Dp,
    val concertRowVerticalPadding: Dp,
    val concertRowImageSize: Dp,
    val concertRowSpaceBetweenInColumn: Dp,
    val borderStrokeActiveButton: Dp,
    val paddingBetweenButtons: Dp,
    val sizeStatusCircle: Dp,
    val statusPlayingVerticalPadding: Dp,
    val statusPlayingHorizontalPadding: Dp,
    val avatarSize: Dp,
    val likeSizeCircle: Dp,
    val likeSizeHeart: Dp,
    val likeHeartTopPadding: Dp,
    val verticalDividerThickness: Dp,
    val rowTopPaddingConcertDescription: Dp,
    val innerPaddingMusicStyle: Dp,
    val startPaddingConcertDetails: Dp,
    val verticalPaddingConcertDetails: Dp,
    val elevationCard: Dp,
    val authBorderThickness: Dp,
    val horizontalPaddingPre: Dp,
    val starSizePre: Dp,
    val labelPadding: Dp,
    val musicStyleImageSize: Dp,
    val musicStyleButtonSize: Dp,
    val divierTopPaddingConcert: Dp,
)

/**
 * Application Theme object
 */
object StreetMusicTheme {
    val colors: StreetMusicColors
        @Composable
        get() = LocalStreetMusicColors.current

    val typography: StreetMusicTypography
        @Composable
        get() = LocalStreetMusicTypography.current

    val shapes: StreetMusicShapes
        @Composable
        get() = LocalStreetMusicShapes.current

    val dimensions: StreetMusicDimensions
        @Composable
        get() = LocalStreetMusicDimensions.current
}

/**
 * Composition Local Providers of styles
 */
val LocalStreetMusicColors = staticCompositionLocalOf<StreetMusicColors> {
    error("No colors provided!")
}

val LocalStreetMusicTypography = staticCompositionLocalOf<StreetMusicTypography> {
    error("No typography provided!")
}

val LocalStreetMusicShapes = staticCompositionLocalOf<StreetMusicShapes> {
    error("No typography provided!")
}

val LocalStreetMusicDimensions = staticCompositionLocalOf<StreetMusicDimensions> {
    error("No typography provided!")
}