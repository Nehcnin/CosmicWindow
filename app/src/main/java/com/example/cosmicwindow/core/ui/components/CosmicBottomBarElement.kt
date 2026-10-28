package com.example.cosmicwindow.core.ui.components

import android.view.RoundedCorner
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicIconGray
import com.example.cosmicwindow.core.ui.theme.CosmicIconSelected
import com.example.cosmicwindow.core.ui.theme.CosmicNavTextSelected

@Composable
fun CosmicBottomBarElement(
    modifier: Modifier = Modifier,
    description: String,
    text: String,
    @DrawableRes icon: Int,
    selected: Boolean = false
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
        Box(
            Modifier
                .width(64.dp)
                .height(32.dp)
                .height(32.dp)
                .clip(RoundedCornerShape(9999.dp))
                .background(if(selected) MaterialTheme.colorScheme.primary else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = description,
                modifier = Modifier.size(24.dp),
                tint = if(selected) CosmicIconSelected else CosmicIconGray
            )
        }

        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = text,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
fun CosmicBottomBarElementPreview(){
    CosmicBottomBar()
}