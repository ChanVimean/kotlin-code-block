package com.example.kura.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kura.navigation.Routes

// The three top-level destinations. Order = left-to-right in the bar.
private data class TopLevelDest(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private val topLevelDestinations = listOf(
    TopLevelDest(Routes.HOME, "Home", Icons.Default.Home),
    TopLevelDest(Routes.BROWSE, "Browse", Icons.Default.Search),
    TopLevelDest(Routes.SETTINGS, "Settings", Icons.Default.Settings)
)

@Composable
fun BottomNavBar(navController: NavHostController) {
    // Read the current route reactively. recomposes on every nav change.
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Only show the bar on the three flat destinations.
    // detail/* and list/* are drill-downs — no bar there.

    val showBar = currentRoute in topLevelDestinations.map { it.route }
    if (!showBar) return

    NavigationBar {
        topLevelDestinations.forEach { dest ->
            NavigationBarItem(
                selected = currentRoute == dest.route,
                onClick = {
                    navController.navigate(dest.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(dest.icon, contentDescription = dest.label) },
                label = { Text(dest.label) }
            )
        }
    }
}