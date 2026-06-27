package com.example.kura.data.model

/* ----------------------------------------------------------------------------
 * Static component data. Swap this object for an API-backed repository later
 * without touching the UI — the screens only depend on List<Component>.
 *
 * NOTE: the first entry (flutter-custom-button) deliberately uses ALL SIX
 * section types. Use it as the test case when building SectionRenderer.kt:
 * if every block renders correctly for this one, the whole `when` is covered.
 *
 * MOCK DATA: expanded for testing. Multiple components per category so the
 * list screen shows several cards and sidebar counts go past (1). Vue populated.
 * ResultImage paths point at assets that may not exist yet — the renderer
 * shows a placeholder until real screenshots are added to app/src/main/assets/.
 * -------------------------------------------------------------------------- */

object FakeData {

    val components: List<Component> = listOf(

        // ====================================================================
        // FLUTTER
        // ====================================================================

        // ---- FULL renderer test: all six section types ----------------------
        Component(
            id = "1",
            slug = "flutter-custom-button",
            title = "Custom Elevated Button",
            domain = Domain.FLUTTER,
            category = Category.BUTTON,
            sections = listOf(
                ComponentSection.Header(
                    title = "Custom Elevated Button",
                    subtitle = "A reusable button with color, icon and loading state"
                ),
                ComponentSection.TextBlock(
                    "A fully reusable ElevatedButton widget with custom colors, " +
                            "padding and an optional leading icon. Drop it in anywhere " +
                            "you need a primary action."
                ),
                ComponentSection.BulletList(
                    listOf(
                        "Customizable background color and label text",
                        "Optional leading icon",
                        "Built-in loading state that disables the button"
                    )
                ),
                ComponentSection.CodeBlock(
                    language = "dart",
                    code = """
                        class CustomButton extends StatelessWidget {
                          final String label;
                          final VoidCallback onPressed;
                          final bool isLoading;

                          const CustomButton({
                            super.key,
                            required this.label,
                            required this.onPressed,
                            this.isLoading = false,
                          });

                          @override
                          Widget build(BuildContext context) {
                            return ElevatedButton(
                              onPressed: isLoading ? null : onPressed,
                              child: isLoading
                                  ? const CircularProgressIndicator()
                                  : Text(label),
                            );
                          }
                        }
                    """.trimIndent()
                ),
                ComponentSection.ResultImage("placeholder.png"),
                ComponentSection.Tags(
                    listOf("Flutter", "Widget", "Button", "Material")
                )
            )
        ),

        // ---- Second Flutter button (so BUTTON count = 2) --------------------
        Component(
            id = "2",
            slug = "flutter-gradient-button",
            title = "Gradient Button",
            domain = Domain.FLUTTER,
            category = Category.BUTTON,
            sections = listOf(
                ComponentSection.Header(
                    title = "Gradient Button",
                    subtitle = "A button with a gradient background fill"
                ),
                ComponentSection.TextBlock(
                    "An elevated-style button that paints a linear gradient behind " +
                            "its label. Good for primary call-to-action buttons."
                ),
                ComponentSection.BulletList(
                    listOf(
                        "Two-color gradient (customizable)",
                        "Rounded corners",
                        "Ripple on tap preserved"
                    )
                ),
                ComponentSection.CodeBlock(
                    language = "dart",
                    code = """
                        class GradientButton extends StatelessWidget {
                          final String label;
                          final VoidCallback onPressed;

                          const GradientButton({
                            super.key,
                            required this.label,
                            required this.onPressed,
                          });

                          @override
                          Widget build(BuildContext context) {
                            return DecoratedBox(
                              decoration: BoxDecoration(
                                gradient: const LinearGradient(
                                  colors: [Color(0xFF54C5F8), Color(0xFF7F52FF)],
                                ),
                                borderRadius: BorderRadius.circular(12),
                              ),
                              child: TextButton(
                                onPressed: onPressed,
                                child: Text(label),
                              ),
                            );
                          }
                        }
                    """.trimIndent()
                ),
                ComponentSection.ResultImage("placeholder.png"),
                ComponentSection.Tags(
                    listOf("Flutter", "Button", "Gradient", "CTA")
                )
            )
        ),

        // ---- Flutter card ---------------------------------------------------
        Component(
            id = "3",
            slug = "flutter-info-card",
            title = "Info Card",
            domain = Domain.FLUTTER,
            category = Category.CARD,
            sections = listOf(
                ComponentSection.Header(
                    title = "Info Card",
                    subtitle = "A simple elevated card with title and body"
                ),
                ComponentSection.TextBlock(
                    "A lightweight Material card for displaying a title and a short " +
                            "description. Padding and elevation are configurable."
                ),
                ComponentSection.CodeBlock(
                    language = "dart",
                    code = """
                        class InfoCard extends StatelessWidget {
                          final String title;
                          final String body;

                          const InfoCard({
                            super.key,
                            required this.title,
                            required this.body,
                          });

                          @override
                          Widget build(BuildContext context) {
                            return Card(
                              elevation: 2,
                              child: Padding(
                                padding: const EdgeInsets.all(16),
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text(title, style: Theme.of(context).textTheme.titleMedium),
                                    const SizedBox(height: 8),
                                    Text(body),
                                  ],
                                ),
                              ),
                            );
                          }
                        }
                    """.trimIndent()
                ),
                ComponentSection.ResultImage("placeholder.png"),
                ComponentSection.Tags(
                    listOf("Flutter", "Card", "Material")
                )
            )
        ),

        // ====================================================================
        // REACT
        // ====================================================================

        // ---- React card -----------------------------------------------------
        Component(
            id = "4",
            slug = "react-profile-card",
            title = "Profile Card",
            domain = Domain.REACT,
            category = Category.CARD,
            sections = listOf(
                ComponentSection.Header(
                    title = "Profile Card",
                    subtitle = "Avatar, name and bio in a bordered card"
                ),
                ComponentSection.TextBlock(
                    "A simple profile card with an avatar, name, role and short bio. " +
                            "Built with plain JSX and utility classes."
                ),
                ComponentSection.CodeBlock(
                    language = "jsx",
                    code = """
                        export function ProfileCard({ name, role, bio, avatar }) {
                          return (
                            <div className="card">
                              <img src={avatar} alt={name} className="avatar" />
                              <h3>{name}</h3>
                              <p className="role">{role}</p>
                              <p className="bio">{bio}</p>
                            </div>
                          );
                        }
                    """.trimIndent()
                ),
                ComponentSection.ResultImage("placeholder.png"),
                ComponentSection.Tags(
                    listOf("React", "Card", "Profile", "JSX")
                )
            )
        ),

        // ---- React button ---------------------------------------------------
        Component(
            id = "5",
            slug = "react-icon-button",
            title = "Icon Button",
            domain = Domain.REACT,
            category = Category.BUTTON,
            sections = listOf(
                ComponentSection.Header(
                    title = "Icon Button",
                    subtitle = "A button with an icon and optional label"
                ),
                ComponentSection.TextBlock(
                    "A reusable button that renders an icon, with an optional text " +
                            "label beside it. Accepts an onClick handler."
                ),
                ComponentSection.BulletList(
                    listOf(
                        "Icon-only or icon + label",
                        "Disabled state",
                        "Forwards any extra props"
                    )
                ),
                ComponentSection.CodeBlock(
                    language = "jsx",
                    code = """
                        export function IconButton({ icon, label, onClick, disabled }) {
                          return (
                            <button
                              className="icon-button"
                              onClick={onClick}
                              disabled={disabled}
                            >
                              <span className="icon">{icon}</span>
                              {label && <span className="label">{label}</span>}
                            </button>
                          );
                        }
                    """.trimIndent()
                ),
                ComponentSection.ResultImage("placeholder.png"),
                ComponentSection.Tags(
                    listOf("React", "Button", "Icon", "JSX")
                )
            )
        ),

        // ====================================================================
        // KOTLIN
        // ====================================================================

        // ---- Kotlin text ----------------------------------------------------
        Component(
            id = "7",
            slug = "kotlin-gradient-text",
            title = "Gradient Text",
            domain = Domain.KOTLIN,
            category = Category.TEXT,
            sections = listOf(
                ComponentSection.Header(
                    title = "Gradient Text",
                    subtitle = "Text with a horizontal color gradient brush"
                ),
                ComponentSection.TextBlock(
                    "A Composable that paints its text with a linear gradient brush. " +
                            "Pass any list of colors to control the gradient."
                ),
                ComponentSection.BulletList(
                    listOf(
                        "Any number of gradient colors",
                        "Reuses your existing TextStyle",
                        "No external dependencies"
                    )
                ),
                ComponentSection.CodeBlock(
                    language = "kotlin",
                    code = """
                        @Composable
                        fun GradientText(
                            text: String,
                            colors: List<Color>,
                            style: TextStyle = LocalTextStyle.current
                        ) {
                            Text(
                                text = text,
                                style = style.copy(
                                    brush = Brush.horizontalGradient(colors)
                                )
                            )
                        }
                    """.trimIndent()
                ),
                ComponentSection.ResultImage("placeholder.png"),
                ComponentSection.Tags(
                    listOf("Kotlin", "Compose", "Text", "Gradient")
                )
            )
        ),

        // ---- Kotlin box -----------------------------------------------------
        Component(
            id = "8",
            slug = "kotlin-centered-box",
            title = "Centered Box",
            domain = Domain.KOTLIN,
            category = Category.BOX,
            sections = listOf(
                ComponentSection.Header(
                    title = "Centered Box",
                    subtitle = "A Box that centers its single child"
                ),
                ComponentSection.TextBlock(
                    "A tiny wrapper around Box that centers whatever you put inside it. " +
                            "Handy for loading spinners and empty states."
                ),
                ComponentSection.CodeBlock(
                    language = "kotlin",
                    code = """
                        @Composable
                        fun CenteredBox(
                            modifier: Modifier = Modifier,
                            content: @Composable () -> Unit
                        ) {
                            Box(
                                modifier = modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                content()
                            }
                        }
                    """.trimIndent()
                ),
                ComponentSection.ResultImage("placeholder.png"),
                ComponentSection.Tags(
                    listOf("Kotlin", "Compose", "Layout", "Box")
                )
            )
        ),

        // ---- Kotlin grid ----------------------------------------------------
        Component(
            id = "9",
            slug = "kotlin-photo-grid",
            title = "Photo Grid",
            domain = Domain.KOTLIN,
            category = Category.GRID,
            sections = listOf(
                ComponentSection.Header(
                    title = "Photo Grid",
                    subtitle = "A two-column lazy grid of images"
                ),
                ComponentSection.TextBlock(
                    "A LazyVerticalGrid laying out items in two columns with even " +
                            "spacing. Swap the cell content for any composable."
                ),
                ComponentSection.BulletList(
                    listOf(
                        "Fixed two-column layout",
                        "Even spacing between cells",
                        "Efficient lazy rendering"
                    )
                ),
                ComponentSection.CodeBlock(
                    language = "kotlin",
                    code = """
                        @Composable
                        fun PhotoGrid(items: List<String>) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(items) { url ->
                                    AsyncImage(model = url, contentDescription = null)
                                }
                            }
                        }
                    """.trimIndent()
                ),
                ComponentSection.ResultImage("placeholder.png"),
                ComponentSection.Tags(
                    listOf("Kotlin", "Compose", "Grid", "Layout")
                )
            )
        ),

        // ====================================================================
        // VUE  (was empty — now populated)
        // ====================================================================

        // ---- Vue button -----------------------------------------------------
        Component(
            id = "10",
            slug = "vue-primary-button",
            title = "Primary Button",
            domain = Domain.VUE,
            category = Category.BUTTON,
            sections = listOf(
                ComponentSection.Header(
                    title = "Primary Button",
                    subtitle = "A single-file component button with a click event"
                ),
                ComponentSection.TextBlock(
                    "A Vue single-file component button that emits a click event and " +
                            "accepts a label prop. Styled with scoped CSS."
                ),
                ComponentSection.BulletList(
                    listOf(
                        "Label via prop",
                        "Emits click event",
                        "Scoped styles"
                    )
                ),
                ComponentSection.CodeBlock(
                    language = "xml",
                    code = $$"""
                        <template>
                          <button class="primary" @click="$emit('click')">
                            {{ label }}
                          </button>
                        </template>

                        <script setup>
                        defineProps({ label: String })
                        defineEmits(['click'])
                        </script>

                        <style scoped>
                        .primary {
                          background: #42b883;
                          color: white;
                          padding: 8px 16px;
                          border-radius: 6px;
                        }
                        </style>
                    """.trimIndent()
                ),
                ComponentSection.ResultImage("placeholder.png"),
                ComponentSection.Tags(
                    listOf("Vue", "Button", "SFC", "Component")
                )
            )
        ),

        // ---- Vue card -------------------------------------------------------
        Component(
            id = "11",
            slug = "vue-stat-card",
            title = "Stat Card",
            domain = Domain.VUE,
            category = Category.CARD,
            sections = listOf(
                ComponentSection.Header(
                    title = "Stat Card",
                    subtitle = "A card showing a label and a large value"
                ),
                ComponentSection.TextBlock(
                    "A compact dashboard card that displays a metric label and a big " +
                            "numeric value. Props drive the content."
                ),
                ComponentSection.CodeBlock(
                    language = "xml",
                    code = """
                        <template>
                          <div class="stat-card">
                            <p class="label">{{ label }}</p>
                            <p class="value">{{ value }}</p>
                          </div>
                        </template>

                        <script setup>
                        defineProps({
                          label: String,
                          value: [String, Number],
                        })
                        </script>
                    """.trimIndent()
                ),
                ComponentSection.ResultImage("placeholder.png"),
                ComponentSection.Tags(
                    listOf("Vue", "Card", "Dashboard", "SFC")
                )
            )
        )
    )

    /** Convenience lookup used by the detail screen's route (by slug). */
    fun bySlug(slug: String): Component? =
        components.firstOrNull { it.slug == slug }
}