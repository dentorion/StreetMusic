package com.example.streetmusic2.ui.navigation

import androidx.navigation.NavHostController

class MainActions(navController: NavHostController) {

    val navigateToMusicianPage: () -> Unit = {
        NavScreen.MusicianPage.apply {
            navController.navigate(this.route)
        }
    }

    val navigateToCityConcerts: () -> Unit = {
        NavScreen.CityConcerts.apply {
            navController.navigate(this.route)
        }
    }

    val navigateToAuthorization: () -> Unit = {
        NavScreen.Authorization.apply {
            navController.navigate(this.route)
        }
    }

    val navigateToPermissions: () -> Unit = {
        NavScreen.Permissions.apply {
            navController.navigate(this.route)
        }
    }

    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}
