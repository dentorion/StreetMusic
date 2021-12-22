package com.entin.streetmusic.core.theme.components

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.entin.streetmusic.R
import com.entin.streetmusic.core.theme.StreetMusicTypography

val fontSegoeUi = FontFamily(
    Font(R.font.segoeui)
)

val typographyPalette = StreetMusicTypography(
    /**
     * Button's text everywhere
     */
    buttonText = TextStyle(
        fontSize = 14.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 1.5.sp
    ),
    /**
     * Artist, CityConcerts screens
     * Artist band name
     */
    bandName = TextStyle(
        fontSize = 15.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.Bold,
    ),
    /**
     * CityConcerts screen
     * Date of the concert creation
     */
    dateCreateConcert = TextStyle(
        fontSize = 11.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.Light,
    ),
    /**
     * Artist screen
     * City and country in header
     */
    cityCountry = TextStyle(
        fontSize = 12.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.Normal,
    ),
    /**
     * Artist screen
     * On-line / Off-line label under concerts list
     */
    onlineOffline = TextStyle(
        fontSize = 14.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
    ),
    /**
     * Artist screen
     * Concert creation date text style
     */
    startDate = TextStyle(
        fontSize = 17.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.Bold,
    ),
    /**
     * Artist screen
     * Concert creation label text style
     */
    startDateLabel = TextStyle(
        fontSize = 17.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.Normal,
    ),
    /**
     * Artist screen
     * Description text style
     */
    description = TextStyle(
        fontSize = 15.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.Normal,
    ),
    /**
     * Permissions screen
     * Error while getting permissions.
     * Cancelled by User
     */
    errorPermission = TextStyle(
        fontSize = 18.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    ),
    /**
     * CityConcerts screen
     * ConcertsRecyclerView
     * list of concerts is empty
     */
    emptyList = TextStyle(
        fontSize = 20.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.Normal,
    ),
    /**
     * Sign in with - fraze
     */
    signIn = TextStyle(
        fontSize = 14.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.Normal,
    ),
    /**
     * Label in PreConcert
     */
    labelField = TextStyle(
        fontSize = 12.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.Normal,
    ),
    /**
     * Auto stop at
     */
    autoStop = TextStyle(
        fontSize = 14.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.Bold,
    ),
    /**
     * Dialog header
     */
    dialogHeader = TextStyle(
        fontSize = 18.sp,
    ),
    /**
     * Dialog content
     */
    dialogContent = TextStyle(
        fontSize = 15.sp,
        fontFamily = fontSegoeUi,
        fontWeight = FontWeight.Normal,
    )
)