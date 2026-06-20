package com.example.kura.data.repository

import com.example.kura.data.model.Category
import com.example.kura.data.model.Component
import com.example.kura.data.model.Domain

interface ComponentRepository {
    // Every component — used by the sidebar to auto-generate sections,
    // and anywhere you need the full list.
    fun getAllComponents() : List<Component>

    // One component by its slug — the detail page uses this.
    // Nullable: returns null if no match (bad/old route, typo, etc.)
    fun getComponentBySlug(slug: String): Component?

    // All components in one domain — e.g. every Flutter component.
    fun getComponentsByDomain(domain: Domain): List<Component>

    // Filtered to domain + category — the Component List screen uses this
    // (e.g. "Flutter / Button" shows only Flutter buttons).
    fun getComponentsByDomainAndCategory(
        domain: Domain,
        category: Category
    ): List<Component>
}