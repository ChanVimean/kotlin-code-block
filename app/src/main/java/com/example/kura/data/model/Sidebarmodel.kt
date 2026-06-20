package com.example.kura.data.model

import androidx.compose.ui.graphics.vector.ImageVector

/* ----------------------------------------------------------------------------
* Sidebar UI model — navigation structure only, separate from content.
*
* This one DOES touch Compose (ImageVector for icons) because it's a UI model,
* not the pure content model above. Keep them in separate files for that reason.
* --------------------------------------------------------------------------
*/

/**
 * A single row in the sidebar. May nest [children] (e.g. a Domain that expands
 * to its Categories). A leaf item has a [route] to navigate to; a parent item
 * typically has children and no route of its own.
 */

data class SidebarItem(
    val label: String,
    val icon: ImageVector? = null,
    val route: String? = null,
    val children: List<SidebarItem> = emptyList()
)

/**
 * A labeled group of sidebar items (e.g. "Introduction", "Components",
 * "App Setting"). The sidebar holds multiple of these as separate lists rather
 * than one flat array, so each group can be built differently — some hardcoded,
 * the Components group auto-generated from the Domain + Category enums.
 */

data class SidebarSection(
    val label: String,
    val item: List<SidebarItem>
)