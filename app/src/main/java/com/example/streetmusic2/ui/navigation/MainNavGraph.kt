package com.example.streetmusic2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.streetmusic2.ui.authorizate.Authorize
import com.example.streetmusic2.ui.authorizate.AuthorizeViewModel
import com.example.streetmusic2.ui.cityconcerts.CityConcerts
import com.example.streetmusic2.ui.cityconcerts.CityConcertsViewModel
import com.example.streetmusic2.ui.musician.PersonalPage
import com.example.streetmusic2.ui.permissions.Permissions
import com.example.streetmusic2.ui.permissions.PermissionsViewModel
import com.example.streetmusic2.ui.start.StartScreen
import com.example.streetmusic2.ui.start.StartScreenViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@Composable
fun MainNavGraph(navController: NavHostController) {

    val actions = remember(navController) {
        MainActions(navController)
    }

    NavHost(navController, startDestination = NavScreen.StartScreen.route) {

        composable(NavScreen.StartScreen.route) {
            val viewModel = hiltViewModel<StartScreenViewModel>()
            StartScreen(
                viewModel = viewModel,
                navToPermissions = actions.navigateToPermissions
            )
        }

        composable(NavScreen.Permissions.route) {
            val viewModel = hiltViewModel<PermissionsViewModel>()
            Permissions(
                viewModel = viewModel,
                navToCityConcerts = actions.navigateToCityConcerts,
                navToAuthorization = actions.navigateToAuthorization
            )
        }

        composable(NavScreen.Authorization.route) {
            val viewModel = hiltViewModel<AuthorizeViewModel>()
            Authorize(viewModel = viewModel)
        }

        composable(NavScreen.CityConcerts.route) {
            val viewModel = hiltViewModel<CityConcertsViewModel>()
            CityConcerts(
                viewModel = viewModel,
                navToMusicianPage = actions.navigateToMusicianPage
            )
        }

        composable(NavScreen.MusicianPage.route) {
            PersonalPage()
        }
    }
}