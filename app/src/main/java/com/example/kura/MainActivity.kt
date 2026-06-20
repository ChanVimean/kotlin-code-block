package com.example.kura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.kura.data.repository.ThemePreferences
import com.example.kura.ui.components.MainScaffold
import com.example.kura.ui.theme.KuraTheme
import com.example.kura.ui.theme.LocalThemeController
import com.example.kura.ui.theme.ThemeController
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val themePrefs = ThemePreferences(applicationContext)

        setContent {
            val isDark by themePrefs.isDarkMode.collectAsState(initial = false)
            val scope = rememberCoroutineScope()

            val controller = ThemeController(
                isDark,
                toggleTheme = { enabled ->
                    scope.launch { themePrefs.setDarkMode(enabled) }
                }
            )

            CompositionLocalProvider(
                LocalThemeController provides controller
            ) {
                KuraTheme(darkTheme = isDark) {
                    MainScaffold()
                }
            }
        }
    }
}
