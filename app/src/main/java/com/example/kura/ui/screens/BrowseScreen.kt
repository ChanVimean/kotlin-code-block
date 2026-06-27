package com.example.kura.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.kura.ui.components.BottomNavBar
import com.example.kura.ui.components.ComponentCard
import com.example.kura.ui.components.FilterDropdown
import com.example.kura.ui.components.KuraTopBar

@Composable
fun BrowseScreen(
    navController: NavHostController
) {
    val repo = RepositoryProvider.componentRepository
    val all = repo.getAllComponents()

    // search
    var searchQuery by remember { mutableStateOf("") }

    // null = "All". Only one domain and one category active at a time
    var selectedDomain by remember { mutableStateOf<Domain?>(null) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    // In-memory filtering - no repo change needed
    val filtered = all.filter { component ->
        (selectedDomain == null || component.domain == selectedDomain) &&
        (selectedCategory == null || component.category == selectedCategory) &&
        (searchQuery.isBlank() ||
            component.title.contains(searchQuery, ignoreCase = true) ||
            component.domain.label.contains(searchQuery, ignoreCase = true)
        )
    }

    Scaffold(
        topBar = { KuraTopBar(navController = navController) },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // ---- CONTROLS (full-width, scrolls away with content) ----
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Column {
                            // header: title + count left, refresh right — one row
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Browse",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "${filtered.size} component${if (filtered.size == 1) "" else "s"}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(Modifier.weight(1f))
                                IconButton(onClick = {
                                    selectedDomain = null
                                    selectedCategory = null
                                    searchQuery = ""
                                }) {
                                    Icon(Icons.Default.Refresh, contentDescription = "Reset filters")
                                }
                            }

                            // search
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Search components") },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                                trailingIcon = {
                                    if (searchQuery.isNotEmpty()) {
                                        IconButton(onClick = { searchQuery = "" }) {
                                            Icon(Icons.Default.Close, contentDescription = "Clear")
                                        }
                                    }
                                },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                            )

                            // filters
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 12.dp),
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
                            }
                        }
                    }

                    // ---- CARDS ----
                    items(filtered) { component ->
                        ComponentCard(component = component) {
                            navController.navigate("detail/${component.slug}")
                        }
                    }
                }
            }
        }
    }

}
