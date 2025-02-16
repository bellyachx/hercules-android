package me.maxhub.hercules.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.maxhub.hercules.data.ExerciseGateway
import me.maxhub.hercules.domain.Exercise
import me.maxhub.hercules.mvi.UIController
import me.maxhub.hercules.mvi.flow.asMutableStateFlow
import me.maxhub.hercules.mvi.navigation.Navigator
import me.maxhub.hercules.mvi.set
import me.maxhub.hercules.navigation.home.HomeDestination
import me.maxhub.hercules.widgets.ExerciseUIModel
import okio.IOException
import javax.inject.Inject

interface HomeController : UIController<HomeState, HomeUIEvent>

@HiltViewModel
class HomeControllerImpl @Inject constructor(
    private val navigator: Navigator<HomeDestination>,
    private val gateway: ExerciseGateway,
    savedStateHandle: SavedStateHandle
) : ViewModel(), HomeController {
    override val state = savedStateHandle.asMutableStateFlow(
        "home",
        HomeState(
            exercises = emptyList()
        )
    )

    init {
        viewModelScope.launch {
            try {
                val exercises = gateway.getExercises()
                state.set {
                    copy(exercises = exercises.map { it.toUiModel() })
                }
            } catch (e: IOException) {
                e
            }
        }
    }

    override fun onEvent(event: HomeUIEvent) {
        when (event) {
            is HomeUIEvent.ExerciseClicked -> {
                navigator.navigate(
                    HomeDestination.ExerciseDetails,
                    ExerciseInput(event.exercise)
                )
            }
        }
    }
}

private fun Exercise.toUiModel(): ExerciseUIModel {
    return ExerciseUIModel(id, name, description, setsCount, repsCount, duration, muscleGroups, difficulty, exerciseType)
}