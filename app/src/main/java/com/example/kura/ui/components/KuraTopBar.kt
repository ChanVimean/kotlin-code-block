package com.example.kura.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kura.data.model.Category
import com.example.kura.data.model.Domain
import com.example.kura.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KuraTopBar(
    navController: NavHostController,
    title: String? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val args = backStackEntry?.arguments

    // --- auto title (unchanged behaviour) ---
    val autoTitle = when (route) {
        "home" -> "Home"
        "browse" -> "Browse"
        "settings" -> "Settings"
        else -> "Kura UI" // Fallback
    }

    // --- auto breadcrumb: read route args, convert enum names → labels ---
    val crumb: String? = when (route) {
        Routes.COMPONENT_LIST -> {
            val d = runCatching { Domain.valueOf(args?.getString("domain") ?: "") }.getOrNull()
            val c = runCatching { Category.valueOf(args?.getString("category") ?: "") }.getOrNull()
            listOfNotNull(d?.label, c?.label).joinToString(" / ").ifBlank { null }
        }
        Routes.COMPONENT_DOMAIN -> {
            runCatching { Domain.valueOf(args?.getString("domain") ?: "") }.getOrNull()?.label
        }
        else -> null // home/browse/settings/detail → no crumb
    }

    TopAppBar(
        title = {
            Column() {
                Text(
                    text = title ?: autoTitle,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (crumb != null) {
                    Text(
                        text = crumb,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }
        },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.popBackStack()}) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = actions,
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = Color.Red
//        )
    )
}