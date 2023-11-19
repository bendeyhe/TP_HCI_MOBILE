package ar.edu.itba.tpHciMobile.ui.main.viewmodels
/*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.tpHciMobile.data.DataSourceException
import ar.edu.itba.tpHciMobile.data.model.Error
import ar.edu.itba.tpHciMobile.data.repository.ExercisesRepository
import ar.edu.itba.tpHciMobile.ui.main.uistates.ExercisesUiState
import ar.edu.itba.tpHciMobile.ui.main.uistates.RoutinesUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ExercisesViewModel (
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {
    var uiState by mutableStateOf(ExercisesUiState())
        private set

    fun getExercises() = runOnViewModelScope(
        {
            exercisesRepository.getExercises(true)
        },
        {
                state, response -> state.copy(exercises = response)
        }
    )

    fun getExercise(exerciseId: Int) = runOnViewModelScope(
        {
            exercisesRepository.getExercise(exerciseId)
        },
        {
                state, response -> state.copy(currentExercise = response)
        }
    )


    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (ExercisesUiState, R) -> ExercisesUiState
    ): Job = viewModelScope.launch {
        uiState = uiState.copy(isFetchingExercise = true, errorFetchingMessage = null)
        runCatching {
            block()
        }.onSuccess { response ->
            uiState = updateState(uiState, response).copy(isFetchingExercise = false)
        }.onFailure { e ->
            uiState = uiState.copy(isFetchingExercise = false, errorFetchingMessage = handleError(e))
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }

}

 */