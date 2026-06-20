package com.example.kura.data.model

import com.example.kura.data.model.Category
import com.example.kura.data.model.Component
import com.example.kura.data.model.ComponentSection
import com.example.kura.data.model.Domain

/* ----------------------------------------------------------------------------
 * Static component data. Swap this object for an API-backed repository later
 * without touching the UI — the screens only depend on List<Component>.
 *
 * NOTE: the first entry (flutter-custom-button) deliberately uses ALL SIX
 * section types. Use it as the test case when building SectionRenderer.kt:
 * if every block renders correctly for this one, the whole `when` is covered.
 * -------------------------------------------------------------------------- */

object FakeData {

    val components: List<Component> = listOf(

        // ---- FULL renderer test: all six section types -----------------------
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
                ComponentSection.ResultImage("assets/flutter_button.png"),
                ComponentSection.Tags(
                    listOf("Flutter", "Widget", "Button", "Material")
                )
            )
        ),

        // ---- React card ------------------------------------------------------
        Component(
            id = "2",
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
                ComponentSection.ResultImage("assets/react_card.png"),
                ComponentSection.Tags(
                    listOf("React", "Card", "Profile", "JSX")
                )
            )
        ),

        // ---- Kotlin text -----------------------------------------------------
        Component(
            id = "3",
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
                ComponentSection.Tags(
                    listOf("Kotlin", "Compose", "Text", "Gradient")
                )
            )
        )
    )

    /** Convenience lookup used by the detail screen's route (by slug). */
    fun bySlug(slug: String): Component? =
        components.firstOrNull { it.slug == slug }
}