package com.example.kura.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kura.data.repository.RepositoryProvider
import com.example.kura.ui.components.SectionRenderer

@Composable
fun ComponentDetailScreen(
    slug: String?
) {
    // Look up the component by slug
    val component = slug?.let {
        RepositoryProvider.componentRepository.getComponentBySlug(it)
    }

    if (component == null) {
        // Safety net: bad slug or not found
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("Component not found.")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Domain / Category label in accent
        Text(
            "${component.domain.name} / ${component.category.name}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        // the block renderer — your proven engine
        SectionRenderer(
            sections = component.sections,
            modifier = Modifier.fillMaxWidth()
        )
    }
}