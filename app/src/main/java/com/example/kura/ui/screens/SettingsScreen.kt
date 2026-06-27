package com.example.kura.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kura.ui.components.BottomNavBar
import com.example.kura.ui.components.KuraTopBar
import com.example.kura.ui.theme.LocalThemeController

@Composable
fun SettingsScreen(
    navController: NavHostController
) {
    val theme = LocalThemeController.current

    Scaffold(
        topBar = { KuraTopBar(navController = navController) },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // ---- General ----
                SettingSection("General") {
                    Text("App Version 0.1", style = MaterialTheme.typography.bodyMedium)
                }

                // ---- APPEARANCE ----
                SettingSection("Appearance") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Dark Mode", style = MaterialTheme.typography.bodyLarge)
                        Switch(
                            checked = theme.isDarkMode,
                            onCheckedChange = { theme.toggleTheme(it) }
                        )
                    }
                }

                // ---- SUPPORT ----
                SettingSection("Support") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Contact: rokrak@gmail.com", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            title.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        content()
    }
}