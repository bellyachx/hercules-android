package me.maxhub.hercules.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalListItem(
    uiModel: HorizontalListItemUIModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = uiModel.title
            )
            if (uiModel.subtitle.isNotBlank()) {
                Text(
                    text = uiModel.subtitle
                )
            }
        }
    }
}

data class HorizontalListItemUIModel(
    val title: String,
    val subtitle: String = ""
)