package com.example.streetmusic2.common.navigation

import androidx.navigation.NavHostController

class NavActions(navController: NavHostController) {

    val navigateToArtistPage: (String) -> Unit = { artistId: String ->
        NavScreen.Artist.apply {
            navController.navigate(
                route = routeWithArgument.replace(
                    oldValue = "{$argument0}",
                    newValue = artistId
                )
            )
        }
    }

    val navigateToCityConcerts: () -> Unit = {
        NavScreen.CityConcerts.apply {
            navController.navigate(this.route) {
                popUpTo(route = NavScreen.StartScreen.route)
            }
        }
    }

    val navigateToPermissions: () -> Unit = {
        NavScreen.Permissions.apply {
            navController.navigate(route = this.route)
        }
    }

    val navigateToAuthorization: () -> Unit = {
        NavScreen.Authorization.apply {
            navController.navigate(this.route) {
                popUpTo(route = NavScreen.StartScreen.route)
            }
        }
    }

    val navigateToPreConcert: (String) -> Unit = { artistId: String ->
        NavScreen.PreConcert.apply {
            navController.navigate(
                route = routeWithArgument.replace(
                    oldValue = "{$argument0}",
                    newValue = artistId
                )
            ) {
                popUpTo(route = NavScreen.StartScreen.route)
            }
        }
    }

    val navigateToConcert: (userId: String, documentId: String) -> Unit =
        { userId: String, documentId: String ->
            NavScreen.Concert.apply {
                navController.navigate(
                    route = routeWithArgument.replace(
                        oldValue = "{$argument0}",
                        newValue = userId,
                    ).replace(
                        oldValue = "{$argument1}",
                        newValue = documentId,
                    )
                ) {
                    popUpTo(route = NavScreen.StartScreen.route)
                }
            }
        }

    val navigateToStart: () -> Unit = {
        NavScreen.StartScreen.apply {
            navController.navigate(this.route) {
                popUpTo(route = NavScreen.StartScreen.route)
            }
        }
    }
}
