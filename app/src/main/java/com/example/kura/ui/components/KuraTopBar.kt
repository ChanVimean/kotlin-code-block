package com.example.kura.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KuraTopBar(
    navController: NavHostController,
    title: String? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Detection: map the current route to its static label.
    // Used only when the caller didn't pass a title.
    val autoTitle = when (currentRoute) {
        "home" -> "Home"
        "browse" -> "Browse"
        "settings" -> "Settings"
        else -> "Kura UI" // Fallback
    }

    // Show back arrow whenever there's something to pop back TO.
    val canPop = navController.previousBackStackEntry != null

    TopAppBar(
        title = { Text(title?: autoTitle) },
        navigationIcon = {
            if (canPop) {
                IconButton(onClick = { navController.popBackStack()}) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = actions
    )
}