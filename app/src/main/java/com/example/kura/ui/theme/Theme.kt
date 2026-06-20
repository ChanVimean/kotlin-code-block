package com.example.kura.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.kura.data.model.Domain


/* ----------------------------------------------------------------------------
 * Color schemes — neutral surfaces, single indigo accent.
 * Only the slots Elegant UI actually uses are customized; the rest fall back
 * to sensible M3 derivations.
 * ----------------------------------------------------------------------------
*/

private val LightColors = lightColorScheme(
    primary = Accent,
    onPrimary = Color.White,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnBackground,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightBorder,
    outlineVariant = LightBorder
)

private val DarkColors = darkColorScheme(
    primary = AccentDark,
    onPrimary = Color(0xFF09090B),
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnBackground,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkBorder,
    outlineVariant = DarkBorder
)

/**
 * Root theme. Controlled by [darkTheme]; pass the value from your
 * theme-toggle state so the TopBar 🌙 switch can flip it.
 */
@Composable
fun ElegantUITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content
    )
}

/* ----------------------------------------------------------------------------
 * Domain → brand color. Used for the sidebar dots and the accent
 * "FLUTTER / BUTTON" labels. Lives here so color logic stays in the theme layer.
 * -------------------------------------------------------------------------- */
fun domainColor(domain: Domain): Color = when (domain) {
    Domain.REACT   -> DomainReact
    Domain.FLUTTER -> DomainFlutter
    Domain.KOTLIN  -> DomainKotlin
    Domain.VUE     -> DomainVue
}