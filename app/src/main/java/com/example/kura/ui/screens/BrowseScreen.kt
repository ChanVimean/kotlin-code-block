package com.example.kura.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kura.data.model.Category
import com.example.kura.data.model.Domain
import com.example.kura.data.repository.RepositoryProvider
import com.example.kura.ui.components.ComponentCard
import com.example.kura.ui.components.FilterDropdown

@Composable
fun BrowseScreen(
    navController: NavHostController
) {
    val repo = RepositoryProvider.componentRepository
    val all = repo.getAllComponents()

    // null = "All". Only one domain and one category active at a time
    var selectedDomain by remember { mutableStateOf<Domain?>(null) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    // In-memory filtering - no repo change needed
    val filtered = all.filter { component ->
        (selectedDomain == null || component.domain == selectedDomain) &&
        (selectedCategory == null || component.category == selectedCategory)
    }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(
            "Browse",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            "${filtered.size} component${if (filtered.size == 1) "" else "s"}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // ------ FILTER ROW ------
        // ------ FILTER ROW ------
        Text(
            "Filter",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterDropdown(
                label = "Framework",
                selected = selectedDomain?.label ?: "All",
                options = Domain.entries.map { it.label },
                onAll = { selectedDomain = null },
                onPick = { index -> selectedDomain = Domain.entries[index] },
                modifier = Modifier.weight(1f)
            )
            FilterDropdown(
                label = "Component",
                selected = selectedCategory?.label ?: "All",
                options = Category.entries.map { it.label },
                onAll = { selectedCategory = null },
                onPick = { index -> selectedCategory = Category.entries[index] },
                modifier = Modifier.weight(1f)
            )
            TextButton(
                onClick = {
                    selectedDomain = null
                    selectedCategory = null
                }
            ) {
                Text("Reset")
            }
        }

        // ---- RESULTS ----
        if (filtered.isEmpty()) {
            Text("No components match these filters")
        } else {
            LazyVerticalGrid(
                GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(filtered) { component ->
                    ComponentCard(component = component) {
                        navController.navigate("detail/${component.slug}")
                    }
                }
            }
        }
    }
}