package com.entin.streetmusic.core.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.entin.streetmusic.ui.screens.artist.Artist
import com.entin.streetmusic.ui.screens.authorizate.Authorize
import com.entin.streetmusic.ui.screens.cityconcerts.CityConcerts
import com.entin.streetmusic.ui.screens.concert.Concert
import com.entin.streetmusic.ui.screens.map.MapObserve
import com.entin.streetmusic.ui.screens.permissions.Permissions
import com.entin.streetmusic.ui.screens.preconcert.PreConcert
import com.entin.streetmusic.ui.screens.start.StartScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@InternalCoroutinesApi
@ExperimentalAnimationApi
@coil.annotation.ExperimentalCoilApi
@ExperimentalCoroutinesApi
@Composable
fun MainNavGraph(navController: NavHostController) {

    Timber.i("Navigation Graph")

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

        composable(
            NavScreen.StartScreen.route
        ) {
            Timber.i("Navigation Graph.StartScreen")
            StartScreen(
                navToPermissions = actions.navigateToPermissions
            )
        }

        composable(NavScreen.Permissions.route) {
            Timber.i("Navigation Graph.Permissions")
            Permissions(
                navToCityConcerts = actions.navigateToCityConcerts,
                navToAuthorization = actions.navigateToAuthorization
            )
        }

        composable(NavScreen.CityConcerts.route) {
            Timber.i("Navigation Graph.CityConcerts")
            CityConcerts(
                navToArtistPage = actions.navigateToArtistPage,
                navToMapObserve = actions.navigateToMapObserve,
            )
        }

        composable(NavScreen.MapObserve.route) {
            Timber.i("Navigation Graph.MapObserve")
            MapObserve(
                navToCityConcerts = actions.navigateToCityConcerts,
                navToArtistPage = actions.navigateToArtistPage,
            )
        }

        composable(
            route = NavScreen.Artist.routeWithArgument,
            arguments = listOf(navArgument(NavScreen.Artist.argument0) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            Timber.i("Navigation Graph.Artist")
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
            Timber.i("Navigation Graph.Authorization")
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
            Timber.i("Navigation Graph.PreConcert")
            backStackEntry.arguments?.let {
                PreConcert(
                    artistId = it.getString(NavScreen.PreConcert.argument0) ?: "",
                    navToConcert = actions.navigateToConcert,
                    navToMain = actions.navigateToStart
                )
            }
        }

        composable(
            route = NavScreen.Concert.routeWithArgument,
            arguments = listOf(
                navArgument(NavScreen.Concert.argument0) {
                    type = NavType.StringType
                },
                navArgument(NavScreen.Concert.argument1) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            Timber.i("Navigation Graph.Concert")
            backStackEntry.arguments?.let {
                Concert(
                    userId = it.getString(NavScreen.Concert.argument0) ?: "",
                    documentId = it.getString(NavScreen.Concert.argument1) ?: "",
                    navToPreConcert = actions.navigateToPreConcert
                )
            }
        }
    }
}