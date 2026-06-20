package com.example.kura.data.repository

import com.example.kura.data.model.Category
import com.example.kura.data.model.Component
import com.example.kura.data.model.Domain
import com.example.kura.data.model.FakeData

class FakeComponentRepository : ComponentRepository {
    override fun getAllComponents(): List<Component> =
        FakeData.components

    override fun getComponentBySlug(slug: String): Component? =
        FakeData.components.find { it.slug == slug }

    override fun getComponentsByDomain(domain: Domain): List<Component> =
        FakeData.components.filter { it.domain == domain }

    override fun getComponentsByDomainAndCategory(
        domain: Domain,
        category: Category
    ): List<Component> =
        FakeData.components.filter {
            it.domain == domain && it.category == category
        }
}