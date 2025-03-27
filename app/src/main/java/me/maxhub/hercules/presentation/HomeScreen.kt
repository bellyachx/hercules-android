package me.maxhub.hercules.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.maxhub.hercules.mvi.UIEvent
import me.maxhub.hercules.mvi.UIState
import me.maxhub.hercules.widgets.ExerciseListItem
import me.maxhub.hercules.widgets.ExerciseUIModel

sealed class HomeUIEvent : UIEvent {
    class ExerciseClicked(val exercise: ExerciseUIModel) : HomeUIEvent()
    object Refresh : HomeUIEvent()
}

data class HomeState(
    val exercises: Collection<ExerciseUIModel>,
    val isRefreshing: Boolean = false
) : UIState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    controller: HomeController = hiltViewModel<HomeControllerImpl>(),
) {
    val state by controller.state.collectAsStateWithLifecycle()
    val refreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { controller.onEvent(HomeUIEvent.Refresh) }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pullRefresh(refreshState)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 12.dp)) {
            state.exercises.forEachIndexed { index, exerciseUIModel ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    ExerciseListItem(
                        exerciseUIModel,
                        onClick = { exercise ->
                            controller.onEvent(HomeUIEvent.ExerciseClicked(exercise))
                        }
                    )
                }
                if (index < state.exercises.size - 1) {
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
