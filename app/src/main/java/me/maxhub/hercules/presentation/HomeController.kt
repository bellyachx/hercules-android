package me.maxhub.hercules.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.maxhub.hercules.data.ExerciseGateway
import me.maxhub.hercules.domain.Exercise
import me.maxhub.hercules.mvi.UIController
import me.maxhub.hercules.mvi.navigation.Navigator
import me.maxhub.hercules.navigation.home.HomeDestination
import me.maxhub.hercules.widgets.ExerciseUIModel
import javax.inject.Inject

interface HomeController : UIController<HomeState, HomeUIEvent>

@HiltViewModel
class HomeControllerImpl @Inject constructor(
    private val navigator: Navigator<HomeDestination>,
    private val gateway: ExerciseGateway,
) : ViewModel(), HomeController {
    private val _state = MutableStateFlow(HomeState(exercises = emptyList()))
    override val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadExercises()
    }

    override fun onEvent(event: HomeUIEvent) {
        when (event) {
            is HomeUIEvent.ExerciseClicked -> {
                navigator.navigate(
                    HomeDestination.ExerciseDetails,
                    ExerciseInput(event.exercise)
                )
            }
            is HomeUIEvent.Refresh -> {
                refreshExercises()
            }
        }
    }

    private fun loadExercises() {
        viewModelScope.launch {
            val exercises = gateway.getExercises().map { it.toUiModel() }
            _state.value = _state.value.copy(exercises = exercises, isRefreshing = false)
        }
    }

    private fun refreshExercises() {
        viewModelScope.launch {
            _state.value = _state.value.copy(exercises = emptyList(), isRefreshing = true)
            loadExercises()
        }
    }
}

private fun Exercise.toUiModel(): ExerciseUIModel {
    return ExerciseUIModel(id, name, description, setsCount, repsCount, duration, muscleGroups, difficulty, exerciseType)
}
