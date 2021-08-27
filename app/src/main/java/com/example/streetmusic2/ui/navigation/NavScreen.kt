package com.example.streetmusic2.ui.navigation

sealed class NavScreen(val route: String) {
    object StartScreen : NavScreen("StartScreen")
    object Permissions : NavScreen("Permissions")
    object CityConcerts : NavScreen("CityConcerts")
    object Authorize : NavScreen("Authorize")
}