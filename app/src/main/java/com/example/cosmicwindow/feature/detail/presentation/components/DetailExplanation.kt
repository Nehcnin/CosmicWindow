package com.example.cosmicwindow.feature.detail.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextDecoration
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme

@Composable
fun DetailExplanation(explanation: String) {
    Text(
        text = "Explanation",
        style = CosmicTheme.typography.titleMediumS.copy(fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(horizontal = CosmicSize.medium),
        color = CosmicTheme.colors.whitePrimary,
    )
    Text(
        text =
            AnnotatedString.fromHtml(
                htmlString =
                    explanation.replaceFirst(
                        Regex(
                            """^(?:\s|<[^>]+>)*Explanation\s*:\s*""",
                            RegexOption.IGNORE_CASE,
                        ),
                        "",
                    ),
                linkStyles =
                    TextLinkStyles(
                        style =
                            SpanStyle(
                                color = CosmicTheme.colors.primary,
                                textDecoration = TextDecoration.Underline,
                            ),
                    ),
            ),
        style = CosmicTheme.typography.bodyMedium,
        color = CosmicTheme.colors.whitePrimary,
        modifier = Modifier.padding(CosmicSize.medium),
    )
}
