package com.example.project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.project.presentation.scaffold.DetailCardScaffold
import com.example.project.presentation.scaffold.MainScreenScaffold

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    NavHost (
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Route.MainScreenScaffold.route) {
            MainScreenScaffold(navController)
        }
        composable(
            Route.DetailCardScaffold.route,
            arguments = listOf(
                navArgument("itemId") {type = NavType.IntType}
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
            DetailCardScaffold(navController, itemId)
        }
    }
}

