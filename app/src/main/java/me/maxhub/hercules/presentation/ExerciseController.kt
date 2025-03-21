package me.maxhub.hercules.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.maxhub.hercules.data.ExerciseGateway
import me.maxhub.hercules.mvi.UIController
import me.maxhub.hercules.mvi.flow.asMutableStateFlow
import me.maxhub.hercules.mvi.navigation.NavigationPayload
import me.maxhub.hercules.mvi.navigation.Navigator
import me.maxhub.hercules.navigation.home.HomeDestination
import me.maxhub.hercules.widgets.ExerciseUIModel
import javax.inject.Inject

interface ExerciseController : UIController<ExerciseState, ExerciseUIEvent>

@HiltViewModel
class ExerciseControllerImpl @Inject constructor(
    private val navigator: Navigator<HomeDestination>,
    private val gateway: ExerciseGateway,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ExerciseController {
    override val state = savedStateHandle.asMutableStateFlow(
        "exercise",
        navigator.requireInput(ExerciseInput::class.java, HomeDestination.ExerciseDetails).toExerciseState()
    )
}

private fun ExerciseInput.toExerciseState(): ExerciseState {
    return ExerciseState(exercise)
}

data class ExerciseInput(
    val exercise: ExerciseUIModel
) : NavigationPayload