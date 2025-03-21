package me.maxhub.hercules.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.maxhub.hercules.mvi.UIEvent
import me.maxhub.hercules.mvi.UIState
import me.maxhub.hercules.widgets.ExerciseUIModel
import me.maxhub.hercules.widgets.HorizontalListItem
import me.maxhub.hercules.widgets.HorizontalListItemUIModel

sealed class ExerciseUIEvent : UIEvent {
}

data class ExerciseState(
    val exercise: ExerciseUIModel
) : UIState

@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    controller: ExerciseController = hiltViewModel<ExerciseControllerImpl>(),
) {
    val state by controller.state.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(top = 8.dp)) {
        HorizontalListItem(HorizontalListItemUIModel("Exercise name", state.exercise.name))
        HorizontalListItem(HorizontalListItemUIModel("Description", state.exercise.description ?: ""))
        HorizontalListItem(HorizontalListItemUIModel("Sets count", state.exercise.setsCount.toString()))
        HorizontalListItem(HorizontalListItemUIModel("Reps count", state.exercise.repsCount.toString()))
        HorizontalListItem(HorizontalListItemUIModel("Duration", state.exercise.duration.toString()))
        HorizontalListItem(HorizontalListItemUIModel("Muscle groups", state.exercise.muscleGroups.toString()))
        HorizontalListItem(HorizontalListItemUIModel("Difficulty", state.exercise.difficulty ?: ""))
        HorizontalListItem(HorizontalListItemUIModel("Exercise type", state.exercise.exerciseType ?: ""))
    }

}
