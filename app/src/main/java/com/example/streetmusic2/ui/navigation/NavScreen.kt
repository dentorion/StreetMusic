package com.example.streetmusic2.ui.navigation

sealed class NavScreen(val route: String) {
    object StartScreen : NavScreen("StartScreen")
    object Permissions : NavScreen("Permissions")
    object CityConcerts : NavScreen("CityConcerts")
    object MusicianPage : NavScreen("MusicianPage")
    object Authorization : NavScreen("Authorization")
}