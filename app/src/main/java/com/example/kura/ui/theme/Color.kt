package com.example.kura.ui.theme

import androidx.compose.ui.graphics.Color

/* ----------------------------------------------------------------------------
 * Brand / accent palette
 * Shadcn-inspired: mostly neutral surfaces, one accent doing the work.
 * -------------------------------------------------------------------------- */

// Accent (used for domain labels, links, active sidebar item, CTA)
val Accent          = Color(0xFF6366F1) // indigo-500
val AccentDark      = Color(0xFF818CF8) // indigo-400 (brighter, for dark bg)

/* ----------------------------------------------------------------------------
 * Light scheme neutrals
 * -------------------------------------------------------------------------- */
val LightBackground = Color(0xFFFFFFFF)
val LightSurface    = Color(0xFFFFFFFF)
val LightSurfaceVariant = Color(0xFFF4F4F5) // zinc-100 — card bg, sidebar
val LightOnBackground   = Color(0xFF18181B) // zinc-900
val LightOnSurfaceVariant = Color(0xFF71717A) // zinc-500 — muted text
val LightBorder     = Color(0xFFE4E4E7) // zinc-200 — dividers, card borders

/* ----------------------------------------------------------------------------
 * Dark scheme neutrals
 * -------------------------------------------------------------------------- */
val DarkBackground  = Color(0xFF09090B) // zinc-950
val DarkSurface     = Color(0xFF09090B)
val DarkSurfaceVariant = Color(0xFF18181B) // zinc-900 — card bg, sidebar
val DarkOnBackground   = Color(0xFFFAFAFA) // zinc-50
val DarkOnSurfaceVariant = Color(0xFFA1A1AA) // zinc-400 — muted text
val DarkBorder      = Color(0xFF27272A) // zinc-800

/* ----------------------------------------------------------------------------
 * Code block — ALWAYS dark (VSCode-style), regardless of app theme
 * -------------------------------------------------------------------------- */
val CodeBlockBackground = Color(0xFF1E1E1E) // VSCode dark
val CodeBlockBorder     = Color(0xFF2D2D2D)

/* ----------------------------------------------------------------------------
 * Domain color dots (sidebar + accent label per framework)
 * -------------------------------------------------------------------------- */
val DomainReact   = Color(0xFF61DAFB)
val DomainFlutter = Color(0xFF54C5F8)
val DomainKotlin  = Color(0xFF7F52FF)
val DomainVue     = Color(0xFF42B883)
