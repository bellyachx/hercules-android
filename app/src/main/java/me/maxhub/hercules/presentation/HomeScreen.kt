package me.maxhub.hercules.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.maxhub.hercules.mvi.UIEvent
import me.maxhub.hercules.mvi.UIState
import me.maxhub.hercules.widgets.ExerciseUIModel
import me.maxhub.hercules.widgets.HorizontalListItemUIModel
import me.maxhub.hercules.widgets.ExerciseListItem

sealed class HomeUIEvent : UIEvent {
    class ExerciseClicked(val exercise: ExerciseUIModel) : HomeUIEvent()
}

data class HomeState(
    val exercises: Collection<ExerciseUIModel>
) : UIState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    controller: HomeController = hiltViewModel<HomeControllerImpl>(),
) {
    val state by controller.state.collectAsStateWithLifecycle()

    Text(
        text = "Exercises",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
    )

    // todo fix alignment. now exercise list is starting from top of the screen. should start after Text element above.
    Column(modifier = Modifier.padding(top = 8.dp)) {
        state.exercises.forEachIndexed { index, exerciseUIModel ->
            ExerciseListItem(
                exerciseUIModel,
                onClick = { exercise ->
                    controller.onEvent(HomeUIEvent.ExerciseClicked(exercise))
                },
            )
            if (index < state.exercises.size - 1) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )
            }
        }
    }

}
