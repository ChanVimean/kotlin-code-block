package com.example.kura.ui.theme

import androidx.compose.runtime.compositionLocalOf

data class ThemeController(
    val isDarkMode: Boolean,
    val toggleTheme: (Boolean) -> Unit
)

// the global "channel" — any composable below the provider can read this
val LocalThemeController = compositionLocalOf<ThemeController> {
    // crashes loudly if you forget to provide it
    error("ThemeController not provided")
}