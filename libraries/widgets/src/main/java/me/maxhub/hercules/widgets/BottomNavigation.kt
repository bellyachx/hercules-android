package me.maxhub.hercules.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigation(
    items: List<BottomNavItem>,
    selectedItem: BottomNavItem,
    modifier: Modifier = Modifier,
    onItemSelected: (BottomNavItem) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(horizontal = 8.dp)
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            BottomNavItemWidget(
                item = item,
                isSelected = item == selectedItem,
                onClick = { onItemSelected(item) },
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 12.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
fun BottomNavItemWidget(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = if (isSelected) Color.White else Color.DarkGray,
                modifier = Modifier
                    .padding(4.dp)
                    .size(24.dp)
            )
        }
        Text(
            text = stringResource(item.labelRes),
            color = if (isSelected) Color.White else Color.DarkGray,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}

data class BottomNavItem(
    @StringRes val labelRes: Int,
    val icon: ImageVector,
    val route: String,
)