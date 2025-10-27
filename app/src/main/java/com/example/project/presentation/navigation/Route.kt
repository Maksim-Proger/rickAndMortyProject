package com.example.project.presentation.navigation

sealed class Route(val route: String) {
    data object MainScreenScaffold : Route(route = "main_screen_scaffold")
    data object DetailCardScaffold : Route(route = "details_card_scaffold/{itemId}") {
        fun getItem(itemId: Int) = "details_card_scaffold/$itemId"
    }
}