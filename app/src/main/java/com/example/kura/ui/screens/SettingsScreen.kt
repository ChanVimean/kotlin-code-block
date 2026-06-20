package com.example.kura.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kura.ui.theme.LocalThemeController

@Composable
fun SettingsScreen() {
    val theme = LocalThemeController.current

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