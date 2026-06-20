package com.example.kura.ui.screens

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kura.data.model.Category
import com.example.kura.data.model.Domain
import com.example.kura.data.repository.RepositoryProvider
import com.example.kura.ui.components.ComponentCard

@Composable
fun ComponentListScreen(
    navController: NavHostController,
    domainName: String?,
    categoryName: String?
) {
    val domain = domainName?.let { runCatching { Domain.valueOf(it) }.getOrNull() }
    val category = categoryName?.let { runCatching { Category.valueOf(it) }.getOrNull() }

    val components = if (domain != null && category != null) {
        RepositoryProvider.componentRepository
            .getComponentsByDomainAndCategory(domain, category)
    } else emptyList()

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // breadcrumb: Flutter / Button
        Text(
            "${domain?.name ?: "?"} / ${category?.name ?: "?"}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (components.isEmpty()) {
            Text("No components here yet")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(components) { component ->
                    ComponentCard(component = component) {
                        navController.navigate("/detail${component.slug}")
                    }
                }
            }
        }
    }
}