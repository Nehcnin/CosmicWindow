package com.example.cosmicwindow.feature.explore.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicTheme

@Composable
fun ExploreSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onMicClick: () -> Unit,
    onSearch: () -> Unit,
    onKeywordChange: (String?) -> Unit,
    keyword: String?,
    modifier: Modifier = Modifier,
) {
    val keywords =
        listOf(
            stringResource(R.string.explore_keyword_james_webb) to R.drawable.ic_jameswebb,
            stringResource(R.string.explore_keyword_mars) to R.drawable.ic_globe,
            stringResource(R.string.explore_keyword_moon) to R.drawable.ic_moon,
            stringResource(R.string.explore_keyword_nebula) to R.drawable.ic_dots,
            stringResource(R.string.explore_keyword_earth) to R.drawable.ic_globe,
            stringResource(R.string.explore_keyword_galaxy) to R.drawable.ic_dots,
            stringResource(R.string.explore_keyword_apollo) to R.drawable.ic_jameswebb,
        )

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .height(CosmicTheme.sizes.cardS),
    ) {
        Row(
            modifier =
                Modifier
                    .padding(horizontal = CosmicTheme.sizes.medium)
                    .fillMaxWidth()
                    .height(CosmicTheme.sizes.largeL)
                    .clip(CircleShape)
                    .background(
                        color = CosmicTheme.colors.surface,
                    ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "",
                tint = CosmicTheme.colors.selected,
                modifier =
                    Modifier
                        .padding(start = CosmicTheme.sizes.medium, end = CosmicTheme.sizes.smallL)
                        .size(CosmicTheme.sizes.iconS),
            )

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(vertical = CosmicTheme.sizes.smallL)
                        .fillMaxHeight()
                        .border(color = CosmicTheme.colors.borderVariant, width = 1.dp),
                singleLine = true,
                keyboardOptions =
                    KeyboardOptions(
                        imeAction = ImeAction.Search,
                    ),
                keyboardActions =
                    KeyboardActions(
                        onSearch = {
                            onSearch()
                        },
                    ),
                textStyle =
                    CosmicTheme.typography.bodyMediumS.copy(
                        fontWeight = FontWeight.Normal,
                        color = CosmicTheme.colors.whitePrimary,
                    ),
                cursorBrush = SolidColor(CosmicTheme.colors.primary),
                decorationBox = { innerTextField ->
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(horizontal = CosmicTheme.sizes.smallL),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        if (query.isEmpty()) {
                            Text(
                                text = stringResource(R.string.explore_search_hint),
                                style = CosmicTheme.typography.bodyMediumS.copy(fontWeight = FontWeight.Normal),
                                color = CosmicTheme.colors.bodyGray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        innerTextField()
                    }
                },
            )

            IconButton(onClick = onMicClick, modifier = Modifier.padding(horizontal = CosmicTheme.sizes.mediumS)) {
                Icon(
                    painter = painterResource(R.drawable.ic_mic),
                    contentDescription = "",
                    tint = CosmicTheme.colors.selected,
                    modifier =
                        Modifier
                            .size(CosmicTheme.sizes.iconS),
                )
            }
        }

        LazyRow(
            modifier = Modifier.padding(vertical = CosmicTheme.sizes.mediumS),
            contentPadding = PaddingValues(horizontal = CosmicTheme.sizes.medium),
            horizontalArrangement = Arrangement.spacedBy(CosmicTheme.sizes.smallL),
        ) {
            items(keywords) { kw ->
                val selected = kw.first == keyword

                KeywordBox(
                    icon = painterResource(kw.second),
                    kw = kw.first,
                    selected = selected,
                    onKeywordChange = onKeywordChange,
                )
            }
        }
    }
}

@Composable
fun KeywordBox(
    icon: Painter,
    kw: String,
    selected: Boolean,
    onKeywordChange: (String?) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .clip(CircleShape)
                .background(
                    if (selected) {
                        CosmicTheme.colors.primary.copy(alpha = 0.2f)
                    } else {
                        CosmicTheme.colors.surface
                    },
                ).clickable {
                    onKeywordChange(if (selected) null else kw)
                }.padding(horizontal = CosmicTheme.sizes.medium, vertical = CosmicTheme.sizes.medium),
        contentAlignment = Alignment.Center,
    ) {
        Row {
            Icon(
                painter = icon,
                contentDescription = "",
                modifier = Modifier.padding(end = CosmicTheme.sizes.smallL).size(CosmicTheme.sizes.iconS),
                tint = CosmicTheme.colors.selected,
            )
            Text(
                text = kw,
                style = CosmicTheme.typography.bodySmall,
                color =
                    if (selected) {
                        CosmicTheme.colors.selected
                    } else {
                        CosmicTheme.colors.bodyGray
                    },
            )
        }
    }
}
