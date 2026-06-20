package com.example.kura.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kura.ui.screens.ComponentDetailScreen
import com.example.kura.ui.screens.ComponentListScreen
import com.example.kura.ui.screens.HomeScreen

object Routes {
    const val HOME = "home"
    const val COMPONENT_LIST = "list/{domain}/{category}"
    const val COMPONENT_DETAIL = "detail/{slug}"
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        // Home Screen
        composable(Routes.HOME) {
            HomeScreen()
        }

        // Component List Screen
        composable(Routes.COMPONENT_LIST) { backStackEntry ->
            val domain = backStackEntry.arguments?.getString("domain")
            val category = backStackEntry.arguments?.getString("category")
            ComponentListScreen(
                navController = navController,
                domainName = domain,
                categoryName = category
            )
        }

        // Component Detail
        composable(Routes.COMPONENT_DETAIL) { backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug")
            ComponentDetailScreen(slug = slug)
        }
    }
}