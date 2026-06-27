package com.example.kura.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kura.data.model.Category
import com.example.kura.data.model.Domain
import com.example.kura.data.repository.RepositoryProvider
import com.example.kura.ui.components.BottomNavBar
import com.example.kura.ui.components.ComponentCard
import com.example.kura.ui.components.KuraTopBar

@Composable
fun ComponentListScreen(
    navController: NavHostController,
    domainName: String?,
    categoryName: String?
) {
    val domain = domainName?.let { runCatching { Domain.valueOf(it) }.getOrNull() }
    val category = categoryName?.let { runCatching { Category.valueOf(it) }.getOrNull() }

    val repo = RepositoryProvider.componentRepository

    val components = when {
        domain != null && category != null ->
            repo.getComponentsByDomainAndCategory(domain, category)
        domain != null ->
            repo.getComponentsByDomain(domain)
        else -> emptyList()

    }

    Scaffold(
        topBar = { KuraTopBar(title = "Back", navController = navController) },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                // breadcrumb adapts: "Flutter / Button"  OR  just "Flutter"
                Text(
                    if (category != null) "${domain?.label ?: "?"} / ${category.label}"
                    else domain?.label ?: "?",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (components.isEmpty()) {
                    Text("No components here yet")
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        items(components) { component ->
                            ComponentCard(component = component) {
                                navController.navigate("detail/${component.slug}")
                            }
                        }
                    }
                }
            }
        }
    }

}