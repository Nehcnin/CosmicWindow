package com.example.cosmicwindow.feature.explore.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.explore.presentation.ExploreViewModel

@Composable
fun ExploreSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onMicClick: () -> Unit,
    onSearch: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(CosmicTheme.sizes.cardS)
    ) {
        Row(
            modifier = Modifier
                .padding(CosmicTheme.sizes.medium)
                .fillMaxWidth()
                .height(CosmicTheme.sizes.largeL)
                .clip(CircleShape)
                .background(
                    color = CosmicTheme.colors.surface
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "",
                tint = CosmicTheme.colors.selected,
                modifier = Modifier
                    .padding(start = CosmicTheme.sizes.medium, end = CosmicTheme.sizes.smallL)
                    .size(CosmicTheme.sizes.iconS)
            )

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = CosmicTheme.sizes.smallL)
                    .fillMaxHeight()
                    .border(color = CosmicTheme.colors.borderVariant, width = 1.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch()
                    }
                ),
                textStyle = CosmicTheme.typography.bodyMediumS.copy(
                    fontWeight = FontWeight.Normal,
                    color = CosmicTheme.colors.whitePrimary
                ),
                cursorBrush = SolidColor(CosmicTheme.colors.primary),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.fillMaxSize().padding(horizontal = CosmicTheme.sizes.smallL),
                        contentAlignment = Alignment.CenterStart) {
                        if (query.isEmpty()) {
                            Text(
                                text = "Search NASA images (e.g. Mars)",
                                style = CosmicTheme.typography.bodyMediumS.copy(fontWeight = FontWeight.Normal),
                                color = CosmicTheme.colors.bodyGray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        innerTextField()
                    }
                }
            )

            IconButton(onClick = onMicClick, modifier = Modifier.padding(horizontal = CosmicTheme.sizes.mediumS)) {
                Icon(
                    painter = painterResource(R.drawable.ic_mic),
                    contentDescription = "",
                    tint = CosmicTheme.colors.selected,
                    modifier = Modifier
                        .size(CosmicTheme.sizes.iconS)
                )
            }
        }
    }
}
