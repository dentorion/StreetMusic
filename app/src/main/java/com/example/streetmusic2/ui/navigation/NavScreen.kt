package com.example.streetmusic2.ui.navigation

sealed class NavScreen(val route: String) {
    object StartScreen : NavScreen("StartScreen")
    object Permissions : NavScreen("Permissions")
    object CityConcerts : NavScreen("CityConcerts")
    object Artist : NavScreen("Artist") {
        const val routeWithArgument: String = "Artist/{artistId}"
        const val argument0: String = "artistId"
    }
    object Authorization : NavScreen("Authorization")
}