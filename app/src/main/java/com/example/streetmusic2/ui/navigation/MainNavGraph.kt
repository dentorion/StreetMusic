package com.example.streetmusic2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.streetmusic2.ui.authorizate.Authorize
import com.example.streetmusic2.ui.authorizate.AuthorizeViewModel
import com.example.streetmusic2.ui.permissions.Permissions
import com.example.streetmusic2.ui.permissions.PermissionsViewModel
import com.example.streetmusic2.ui.start.StartScreen
import com.example.streetmusic2.ui.start.StartScreenViewModel

@Composable
fun MainNavGraph(navController: NavHostController) {

    val actions = remember(navController) {
        MainActions(navController)
    }

    NavHost(navController, startDestination = "startScreen") {

        composable("startScreen") {
            val viewModel = hiltViewModel<StartScreenViewModel>()
            StartScreen(
                viewModel = viewModel,
                navToPermissions = actions.navigateToPermissions
            )
        }

        composable("permissions") {
            val viewModel = hiltViewModel<PermissionsViewModel>()
            Permissions(viewModel = viewModel)
        }

        composable("authorization") {
            val viewModel = hiltViewModel<AuthorizeViewModel>()
            Authorize(viewModel = viewModel)
        }

    }
}