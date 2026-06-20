package com.rohan.chessfetch.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")

    data object Player : Screen("player/{username}") {
        fun createRoute(username: String) = "player/$username"
    }

    data object Games : Screen("games/{username}") {
        fun createRoute(username: String) = "games/$username"
    }
}
