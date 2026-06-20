package com.example.kura.data.model

/* ----------------------------------------------------------------------------
 * Real data model — the actual component content.
 *
 * No Compose / Android imports on purpose. This layer stays pure so it can be
 * swapped from FakeData to a real API later without touching the UI.
 * -------------------------------------------------------------------------- */

/**
 * A single block on a component's detail page.
 *
 * The detail page is built by looping a [Component.sections] list and rendering
 * the matching composable per type. Order in the list == order on screen.
 * Because this is a sealed class, the `when` in the renderer is exhaustive:
 * add a new section type and the compiler forces you to handle it everywhere.
 */

sealed class ComponentSection {
    data class Header(
        val title: String,
        val subtitle: String? = null,
    ) : ComponentSection()

    data class TextBlock(
        val body: String
    ) : ComponentSection()

    data class BulletList(
        val items : List<String>
    ) : ComponentSection()

    data class CodeBlock(
        val language: String,
        val code: String
    ) : ComponentSection()

    data class ResultImage(
        val assetPath: String
    ) : ComponentSection()

    data class Tags(
        val tags: List<String>
    ) : ComponentSection()
}

/**
 * One reusable UI component entry in the vault.
 * [sections] IS the page layout — render it in order.
 */
data class Component(
    val id: String,
    val slug: String,
    val title: String,
    val domain: Domain,
    val category: Category,
    val sections: List<ComponentSection>
)

/* ----------------------------------------------------------------------------
 * Enums carry their human-readable [label]. Color is NOT here — it lives in the
 * theme layer (domainColor()) so this model stays free of Compose imports.
 * -------------------------------------------------------------------------- */

enum class Domain(val label: String) {
    REACT("React"),
    FLUTTER("Flutter"),
    KOTLIN("Kotlin"),
    VUE("Vue")
}

enum class Category(val label: String) {
    BUTTON("Button"),
    CARD("Card"),
    TEXT("Text"),
    NAVBAR("Navbar"),
    FOOTER("Footer"),
    BOX("Box"),
    GRID("Grid")
}