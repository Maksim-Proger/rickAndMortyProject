package com.example.project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.project.presentation.scaffold.AdvancedSearchScreen
import com.example.project.presentation.scaffold.DetailCardScaffold
import com.example.project.presentation.scaffold.MainScreenScaffold
import com.example.project.presentation.viewmodel.MainViewModel

@Composable
fun NavGraph(
    startDestination: String,
    viewModel: MainViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Route.MainScreenScaffold.route) {
            MainScreenScaffold(navController, viewModel)
        }
        composable(Route.AdvancedSearchScreen.route) {
            AdvancedSearchScreen(navController, viewModel)
        }
        composable(
            Route.DetailCardScaffold.route,
            arguments = listOf(
                navArgument("itemId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
            DetailCardScaffold(navController, itemId)
        }
    }
}

