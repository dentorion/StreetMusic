package com.example.streetmusic2.common.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.streetmusic2.ui.artist.Artist
import com.example.streetmusic2.ui.authorizate.Authorize
import com.example.streetmusic2.ui.cityconcerts.CityConcerts
import com.example.streetmusic2.ui.concert.Concert
import com.example.streetmusic2.ui.permissions.Permissions
import com.example.streetmusic2.ui.preconcert.PreConcert
import com.example.streetmusic2.ui.start.StartScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@Composable
fun MainNavGraph(navController: NavHostController) {

    Log.i("MyMusic", "2.Navigation Graph")

    val actions = remember(navController) {
        NavActions(navController)
    }

    NavHost(
        navController = navController,
        startDestination = NavScreen.StartScreen.route,
    ) {

        /**
         * LISTENER
         */

        composable(NavScreen.StartScreen.route) {
            Log.i("MyMusic", "2.Navigation Graph.StartScreen")
            StartScreen(
                navToPermissions = actions.navigateToPermissions
            )
        }

        composable(NavScreen.Permissions.route) {
            Log.i("MyMusic", "2.Navigation Graph.Permissions")
            Permissions(
                navToCityConcerts = actions.navigateToCityConcerts,
                navToAuthorization = actions.navigateToAuthorization
            )
        }

        composable(NavScreen.CityConcerts.route) {
            Log.i("MyMusic", "2.Navigation Graph.CityConcerts")
            CityConcerts(
                navToArtistPage = actions.navigateToArtistPage
            )
        }

        composable(
            route = NavScreen.Artist.routeWithArgument,
            arguments = listOf(navArgument(NavScreen.Artist.argument0) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            Log.i("MyMusic", "2.Navigation Graph.Artist")
            backStackEntry.arguments?.let {
                Artist(
                    artistId = it.getString(NavScreen.Artist.argument0) ?: "",
                )
            }
        }

        /**
         * ARTIST
         */

        composable(NavScreen.Authorization.route) {
            Log.i("MyMusic", "7.Navigation Graph.Authorization")
            Authorize(
                navToPreConcert = actions.navigateToPreConcert,
                navToConcert = actions.navigateToConcert
            )
        }

        composable(
            route = NavScreen.PreConcert.routeWithArgument,
            arguments = listOf(navArgument(NavScreen.Artist.argument0) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            Log.i("MyMusic", "2.Navigation Graph.PreConcert")
            backStackEntry.arguments?.let {
                PreConcert(
                    currentArtisId = it.getString(NavScreen.Artist.argument0) ?: "",
                    navToConcert = actions.navigateToConcert,
                    navToMain = actions.navigateToStart
                )
            }
        }

        composable(
            route = NavScreen.Concert.routeWithArgument,
            arguments = listOf(navArgument(NavScreen.Artist.argument0) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            Log.i("MyMusic", "2.Navigation Graph.Concert")
            backStackEntry.arguments?.let {
                Concert(
                    currentArtisId = it.getString(NavScreen.Artist.argument0) ?: "",
                )
            }
        }
    }
}