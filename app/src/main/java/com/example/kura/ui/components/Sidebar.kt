package com.example.kura.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kura.data.model.Category
import com.example.kura.data.model.Domain
import com.example.kura.data.model.SidebarSection
import com.example.kura.data.repository.RepositoryProvider
import kotlin.math.exp

@Composable
fun Sidebar(
    navController: NavHostController,
    onItemClick: () -> Unit
) {
    val repo = RepositoryProvider.componentRepository

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Logo / app name
        Text(
            "Kura UI",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // ---- INTRODUCTION (hardcoded) ----
        SidebarSectionHeader("Introduction")
        SidebarRow("Home") {
            navController.navigate("Home")
            onItemClick()
        }
        SidebarRow("Document") {
            // TODO: document route later
            onItemClick()
        }

        Spacer(Modifier.height(16.dp))

        // ---- COMPONENTS (auto-generated, collapsible) ----
        SidebarSectionHeader("Components")
        Domain.entries.forEach { domain ->
            // Each domain remembers its own expanded state, default collapsed
            var expanded by remember { mutableStateOf(false) }

            // Domain row — the whole row is tappable to toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    domain.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector =
                        if (expanded) Icons.Default.KeyboardArrowDown
                        else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = if (expanded) " Collapse" else "Expand"
                )
            }

            if (expanded) {
                Category.entries.forEach { category ->
                    val hasComponents = repo
                        .getComponentsByDomainAndCategory(domain, category)
                        .isNotEmpty()

                    if (hasComponents) {
                        SidebarRow(
                            "${category.name} (${repo.getComponentsByDomainAndCategory(domain, category).size})",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        ) {
                            navController.navigate(("list/${domain.name}/${category.name}"))
                            onItemClick()
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // ---- APP SETTING (hardcoded) ---
        SidebarRow("Setting") {
            navController.navigate("settings")
            onItemClick()
        }
    }
}

@Composable
private fun SidebarSectionHeader(label: String) {
    Text(
        label.uppercase(),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun SidebarRow(label: String, style: TextStyle = MaterialTheme.typography.bodyLarge, onClick: () -> Unit) {
    Text(
        label,
        style = style,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp)
        )
}