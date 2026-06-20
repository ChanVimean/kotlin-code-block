package com.example.kura.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.kura.data.model.ComponentSection

/* ----------------------------------------------------------------------------
 * The block renderer — core engine.
 *
 * Loops a component's sections and renders the matching composable per type.
 * Order in the list == order on screen. Because ComponentSection is sealed,
 * the `when` below is exhaustive — add a new section type and the compiler
 * forces you to handle it right here.
 *
 * FIRST PASS: the five simple composables are placeholders that just show what
 * they received, so you can confirm the loop + ordering work against the
 * flutter-custom-button test component. Flesh them out in pass two.
 * CodeBlock already lives in its own file (WebView wrapper) per the file plan.
 * --------------------------------------------------------------------------
 */


@Composable
fun SectionRenderer(
    sections: List<ComponentSection>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        sections.forEach { section ->
            when (section) {
                is ComponentSection.Header -> HeaderComposable(section)
                is ComponentSection.TextBlock -> TextComposable(section)
                is ComponentSection.BulletList -> BulletListComposable(section)
                is ComponentSection.CodeBlock -> CodeBlockComposable(section)
                is ComponentSection.ResultImage -> ResultImageComposable(section)
                is ComponentSection.Tags -> TagsComposable(section)
            }
        }
    }
}

/* ----------------------------------------------------------------------------
 * Placeholder composables (pass one).
 * Each just proves the section reached the screen with the right data.
 * --------------------------------------------------------------------------
 */
@Composable
private fun HeaderComposable(section: ComponentSection.Header) {
    Placeholder("HEADER") {
        Text(section.title, style = MaterialTheme.typography.headlineSmall)
        section.subtitle?.let {
            Text(it, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun TextComposable(section: ComponentSection.TextBlock) {
    Placeholder("TEXTBLOCK") {
        Text(section.body, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun BulletListComposable(section: ComponentSection.BulletList) {
    Placeholder("BULLET LIST") {
        section.items.forEach { item ->
            Text("• $item", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun ResultImageComposable(section: ComponentSection.ResultImage) {
    Placeholder("RESULT IMAGE") {
        Text("image → ${section.assetPath}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun TagsComposable(section: ComponentSection.Tags) {
    Placeholder("TAGS") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            section.tags.forEach { tag ->
                Text("#$tag", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

/**
 * Temporary wrapper so each placeholder block is visually obvious on screen
 * (labeled, boxed). Delete this once the real composables are in.
 */
@Composable
private fun Placeholder(
    label: String,
    content: @Composable () -> Unit
) {
    Column(Modifier.padding(12.dp)) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
        content()
    }
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun CodeBlockComposable(section: ComponentSection.CodeBlock) {
//    val html = remember(section.code, section.language) {
//        buildHighlightHtml(section.code, section.language)
//    }
//    AndroidView(
//        factory = { context ->
//            WebView(context).apply {
//                settings.javaScriptEnabled = true
//                setBackgroundColor(android.graphics.Color.TRANSPARENT)
//            }
//        },
//        update = { webView ->
//            webView.loadDataWithBaseURL(
//                "https://localhost/",
//                html,
//                "text/html",
//                "utf-8",
//                null
//            )
//        },
//        modifier = Modifier.fillMaxWidth()
//    )

    Placeholder("CODE (${section.language})") {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                section.code,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily.Monospace
                ),
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

private fun buildHighlightHtml(code: String, language: String): String {
    val escaped = code
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")

    return """
        <!DOCTYPE html>
        <html>
            <head>
                <link rel="stylesheet" href="file:///android_asset/prism.css">
                <style>
                    body { margin: 0; background: transparent; }
                    pre { margin: 0; padding: 12px; font-size: 14px; overflow-x: auto; }
                </style>
            </head>
            <body>
                <pre><code class="language-$language">$escaped</code></pre>
                <script src="file:///android_asset/prism.js"></script>
            </body>
        </html>
    """.trimIndent()
}