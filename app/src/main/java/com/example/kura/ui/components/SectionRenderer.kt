package com.example.kura.ui.components

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.kura.R
import com.example.kura.data.model.ComponentSection
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

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
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(section.title, style = MaterialTheme.typography.headlineSmall)
        section.subtitle?.let {
            Text(
                it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun TextComposable(section: ComponentSection.TextBlock) {
    Text(section.body, style = MaterialTheme.typography.bodyLarge)
}


@Composable
private fun BulletListComposable(section: ComponentSection.BulletList) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        section.items.forEach { item ->
            Text("• $item", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@SuppressLint("LocalContextResourcesRead", "DiscouragedApi")
@Composable
private fun ResultImageComposable(section: ComponentSection.ResultImage) {
    val context = LocalContext.current
    val resId = remember(section.assetPath) {
        context.resources.getIdentifier(
            section.assetPath,   // e.g. "flutter_button"
            "drawable",
            context.packageName
        )
    }

    val finalResId = if (resId != 0) resId else R.drawable.placeholder

    Image(
        painter = painterResource(id = finalResId),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
private fun TagsComposable(section: ComponentSection.Tags) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        section.tags.forEach { tag ->
            Text("#$tag", style = MaterialTheme.typography.labelMedium)
        }
    }
}

/**
 * Temporary wrapper so each placeholder block is visually obvious on screen
 * (labeled, boxed). Delete this once the real composables are in.
 */
//@Composable
//private fun Placeholder(
//    label: String,
//    content: @Composable () -> Unit
//) {
//    Column(Modifier.padding(12.dp)) {
//        Text(
//            label,
//            style = MaterialTheme.typography.labelSmall,
//            color = MaterialTheme.colorScheme.primary
//        )
//        content()
//    }
//}


@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun CodeBlockComposable(section: ComponentSection.CodeBlock) {
    val html = remember(section.code, section.language) {
        buildHighlightHtml(section.code, section.language)
    }
    var webViewHeight by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    // --- clipboard + copied-state feedback ---
    val context = LocalContext.current
    var copied by remember { mutableStateOf(false) }
    LaunchedEffect(copied) {
        if (copied) {
            delay(1500.milliseconds) // Revert the checkmark after 1.5s
            copied = false
        }
    }

    // STRUCTURE:
    // Column
    // ├─ Row  [badge] ─ Spacer ─ [copy]
    // └─ AndroidView (your WebView)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF282C34)) // atom-one dark bg
    ) {
        // --- header row: badge + copy ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF3A3F4B))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = languageLabel(section.language),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFFABB2BF),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Copy Button
            IconButton(
                onClick = {
                    val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                    cm.setPrimaryClip(ClipData.newPlainText("code", section.code))
                    copied = true
                },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    imageVector = if (copied) Icons.Default.Check
                        else Icons.Default.ContentCopy,
                    contentDescription = if (copied) "Copied" else "Copy code",
                    tint = if (copied) Color(0xFF98C379)
                        else Color(0xFFABB2BF),
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // --- WebView ---
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    setBackgroundColor(android.graphics.Color.TRANSPARENT)
                    isVerticalScrollBarEnabled = false
                    isScrollContainer = false
                    addJavascriptInterface(object {
                        @android.webkit.JavascriptInterface
                        fun reportHeight(heightPx: Float) {
                            post {
                                webViewHeight = with(density) { heightPx.dp.roundToPx() }
                            }
                        }
                    }, "AndroidBridge")
                }
            },
            update = { webView ->
                webView.loadDataWithBaseURL(
                    "file:///android_asset/",
                    html,
                    "text/html",
                    "utf-8",
                    null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (webViewHeight > 0) Modifier.height(with(density) { webViewHeight.toDp() })
                    else Modifier
                )
        )
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
                <link rel="stylesheet" href="file:///android_asset/highlight.min.css">
                <style>
                    body { margin: 0; background: transparent; }
                    pre { margin: 0; padding: 12px; font-size: 14px; overflow-x: auto; }
                </style>
            </head>
            <body>
                <pre><code class="language-$language">$escaped</code></pre>
                <script src="file:///android_asset/highlight.min.js"></script>
                <script>
                    hljs.highlightAll();
                    window.onload = function() {
                        AndroidBridge.reportHeight(document.body.scrollHeight);
                    };
                </script>
            </body>
        </html>
    """.trimIndent()
}

private fun languageLabel(language: String): String = when (language.trim().lowercase()) {
    "dart"       -> "Flutter"
    "javascript" -> "React"
    "kotlin"     -> "Kotlin"
    "xml"        -> "Vue"
    else         -> language.lowercase().replaceFirstChar { it.uppercase() }
}