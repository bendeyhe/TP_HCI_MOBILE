package ar.edu.itba.tpHciMobile.ui.main.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.tpHciMobile.data.DataSourceException
import ar.edu.itba.tpHciMobile.data.model.Error
import ar.edu.itba.tpHciMobile.data.repository.ExercisesRepository
import ar.edu.itba.tpHciMobile.data.repository.RoutinesCycleRepository
import ar.edu.itba.tpHciMobile.data.repository.RoutinesRepository
import ar.edu.itba.tpHciMobile.data.repository.UserRepository
import ar.edu.itba.tpHciMobile.ui.main.uistates.MainUiState
import ar.edu.itba.tpHciMobile.ui.main.uistates.RoutinesUiState
import ar.edu.itba.tpHciMobile.ui.model.CyclesDetail
import ar.edu.itba.tpHciMobile.util.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RoutinesViewModel(
    private val routinesRepository: RoutinesRepository,
    private val userRepository: UserRepository,
    private val routinesCycleRepository: RoutinesCycleRepository,
    private val exercisesRepository: ExercisesRepository,
    sessionManager: SessionManager,
) : ViewModel() {
    var uiState by mutableStateOf(RoutinesUiState())
        private set

    var userState by mutableStateOf(MainUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set


    fun getRoutines() = runOnViewModelScope(
        {
            routinesRepository.getRoutines(true)
        },
        { state, response ->
            state.copy(routines = response)
        }
    )

    fun getFavoriteRoutines() = runOnViewModelScope(
        {
            routinesRepository.getFavoriteRoutines(true)
        },
        { state, response ->
            state.copy(favouriteRoutines = response)
        }
    )

    fun addRoutineToFavorites(routineId: Int) = runOnViewModelScope(
        {
            routinesRepository.addFavoriteRoutine(routineId)
        },
<<<<<<< Updated upstream
        { state, _ ->
            state.copy(
                favouriteRoutines = state.favouriteRoutines?.map {
                    if (it.id == routineId) {
                        it.copy(liked = true)
                    } else {
                        it
                    }
                }
            )
        }
    )

    fun removeRoutineFromFavorites(routineId: Int) = runOnViewModelScope(
        {
            routinesRepository.removeFavoriteRoutine(routineId)
        },
        { state, _ ->
            state.copy(
                favouriteRoutines = state.favouriteRoutines?.map {
                    if (it.id == routineId) {
                        it.copy(liked = false)
                    } else {
                        it
                    }
                }
            )
        }
=======
        { state, _ -> state }
>>>>>>> Stashed changes
    )

    fun getRoutinesOrderBy(orderBy: String, direction: String) = runOnViewModelScope(
        {
            routinesRepository.getRoutinesOrderBy(orderBy, direction)
        },
        { state, response ->
            state.copy(routines = response)
        }
    )

    fun getExercisesByCycle(cycleId: Int) = runOnViewModelScope(
        {
            exercisesRepository.getExercisesByCycle(cycleId)
        },
        { state, response ->
            state.copy(exercises = response)
        }
    )

    fun getExerciseByCycle(cycleId: Int, exerciseId: Int) = runOnViewModelScope(
        {
            exercisesRepository.getExerciseByCycle(cycleId, exerciseId)
        },
        { state, response ->
            state.copy(currentExercise = response)
        }
    )

    fun getRoutine(routineId: Int) = viewModelScope.launch {
        uiState.cycleDetailList = emptyList()
        uiState = uiState.copy(
            isFetchingR = true,
            fetchRoutineErrorStringId = null
        )
        runCatching {
            routinesRepository.getRoutine(routineId)
        }.onSuccess { rout ->
            println(rout.name)
            uiState = uiState.copy(
                currentRoutine = rout
            )
            println(uiState.currentRoutine?.name)
            getRoutinesCycles(routineId).join()

            for (cycle in uiState.routineCycles) {
                getExercisesByCycle(cycle.id).join()
                uiState.cycleDetailList = uiState.cycleDetailList.plus(
                    CyclesDetail(
                        exercises = uiState.exercises,
                        cycle
                    )
                )

            }
            uiState = uiState.copy(
                isFetchingR = false
            )
        }.onFailure { e ->
            uiState = uiState.copy(
                isFetchingR = false
            )
        }


    }

        fun getCurrentUserRoutines() = runOnViewModelScope(
            {
                userRepository.getCurrentUserRoutines(true)
            },
            { state, response ->
                state.copy(userRoutines = response)
            }
        )

        fun getRoutinesCycles(routineId: Int) = runOnViewModelScope(
            {
                routinesCycleRepository.getRoutineCycles(routineId, true)
            },
            { state, response ->
                state.copy(routineCycles = response)
            }
        )

        fun getRoutineCycle(routineId: Int, cycleId: Int) = runOnViewModelScope(
            {
                routinesCycleRepository.getRoutineCycle(routineId, cycleId)
            },
            { state, response ->
                state.copy(currentRoutineCycle = response)
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
                uiState =
                    uiState.copy(
                        isFetchingRoutine = false,
                        fetchRoutineErrorStringId = handleError(e)
                    )
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