package com.example.streetmusic2.common.navigation

sealed class NavScreen(val route: String) {
    /**
     * Start Screen. Maybe will FlashScreen
     */
    object StartScreen : NavScreen("StartScreen")

    /**
     * Getting permissions if first launch
     */
    object Permissions : NavScreen("Permissions")

    /**
     * Show concerts of the city
     */
    object CityConcerts : NavScreen("CityConcerts")

    /**
     * Show personal data and on-line / off-line concerts of the artist
     */
    object Artist : NavScreen("Artist") {
        const val routeWithArgument: String = "Artist/{artistId}"
        const val argument0: String = "artistId"
    }

    /**
     * For Artists authorization page
     */
    object Authorization : NavScreen("Authorization")

    /**
     * Settings before running on-line concert
     */
    object PreConcert : NavScreen("PreConcert") {
        const val routeWithArgument: String = "PreConcert/{artistId}"
        const val argument0: String = "artistId"
    }

    /**
     * On-line concert page
     */
    object Concert : NavScreen("Concert") {
        const val routeWithArgument: String = "Concert/{artistId}"
        const val argument0: String = "artistId"
    }
}