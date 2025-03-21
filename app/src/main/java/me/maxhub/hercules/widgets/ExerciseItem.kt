package me.maxhub.hercules.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.time.Duration

data class ExerciseUIModel(
    val id: String,
    val name: String,
    val description: String?,
    val setsCount: Int?,
    val repsCount: Int?,
    val duration: Duration?,
    val muscleGroups: Collection<String>?,
    val difficulty: String?,
    val exerciseType: String?,
)

@Composable
fun ExerciseListItem(
    uiModel: ExerciseUIModel,
    onClick: (ExerciseUIModel) -> Unit = { _ -> },
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 12.dp)
            .clickable { onClick(uiModel) },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = uiModel.name
            )
            if (uiModel.exerciseType?.isNotBlank() == true) {
                Text(
                    text = uiModel.exerciseType
                )
            }
        }
    }
}
