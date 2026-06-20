package com.rohan.chessfetch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rohan.chessfetch.ui.screens.GamesScreen
import com.rohan.chessfetch.ui.screens.HomeScreen
import com.rohan.chessfetch.ui.screens.PlayerScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onPlayerSelected = { username ->
                    navController.navigate(Screen.Player.createRoute(username))
                }
            )
        }

        composable(
            route = Screen.Player.route,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStack ->
            val username = backStack.arguments?.getString("username") ?: return@composable
            PlayerScreen(
                username = username,
                onBack = { navController.popBackStack() },
                onViewGames = { navController.navigate(Screen.Games.createRoute(username)) }
            )
        }

        composable(
            route = Screen.Games.route,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStack ->
            val username = backStack.arguments?.getString("username") ?: return@composable
            GamesScreen(
                username = username,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
