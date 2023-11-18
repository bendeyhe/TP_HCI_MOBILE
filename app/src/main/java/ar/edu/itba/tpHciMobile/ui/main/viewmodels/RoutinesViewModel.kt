package ar.edu.itba.tpHciMobile.ui.main.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.tpHciMobile.data.DataSourceException
import ar.edu.itba.tpHciMobile.data.model.Error
import ar.edu.itba.tpHciMobile.data.repository.RoutinesCycleRepository
import ar.edu.itba.tpHciMobile.data.repository.RoutinesRepository
import ar.edu.itba.tpHciMobile.data.repository.UserRepository
import ar.edu.itba.tpHciMobile.ui.main.uistates.MainUiState
import ar.edu.itba.tpHciMobile.ui.main.uistates.RoutinesUiState
import ar.edu.itba.tpHciMobile.util.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RoutinesViewModel (
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val routinesRepository: RoutinesRepository,
    private val routinesCycleRepository: RoutinesCycleRepository
    ) : ViewModel() {
    var uiState by mutableStateOf(RoutinesUiState())
        private set
    fun getRoutines() = runOnViewModelScope(
        {
            routinesRepository.getRoutines(true)
        },
        {
                state, response -> state.copy(routines = response)
        }
    )

    fun getRoutinesOrderBy(orderBy: String, direction: String) = runOnViewModelScope(
        {
            routinesRepository.getRoutinesOrderBy(orderBy, direction)
        },
        {
                state, response -> state.copy(routines = response)
        }
    )

    fun getRoutine(routineId: Int) = runOnViewModelScope(
        {
            routinesRepository.getRoutine(routineId)
        },
        {
                state, response -> state.copy(currentRoutine = response)
        }
    )





    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (RoutinesUiState, R) -> RoutinesUiState
    ): Job = viewModelScope.launch {
        uiState = uiState.copy(isFetchingRoutine = true, fetchRoutineErrorStringId = null)
        runCatching {
            block()
        }.onSuccess { response ->
            uiState = updateState(uiState, response).copy(isFetchingRoutine = false)
        }.onFailure { e ->
            uiState = uiState.copy(isFetchingRoutine = false, fetchRoutineErrorStringId = handleError(e))
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