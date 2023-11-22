package ar.edu.itba.tpHciMobile.ui.main.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.tpHciMobile.data.DataSourceException
import ar.edu.itba.tpHciMobile.data.model.Error
import ar.edu.itba.tpHciMobile.data.model.Review
import ar.edu.itba.tpHciMobile.data.repository.ExercisesRepository
import ar.edu.itba.tpHciMobile.data.model.Routine
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

    fun getFavsRoutines() = viewModelScope.launch {
        uiState = uiState.copy(isFetchingRoutine = true, fetchRoutineErrorStringId = null)
        getFavoriteRoutines().join()
        uiState = uiState.copy(updatedFavs = false)
    }

    fun getFavoriteRoutines() = viewModelScope.launch {
        uiState = uiState.copy(isFetchingRoutine = true, fetchRoutineErrorStringId = null)
        runCatching {
            routinesRepository.getFavoriteRoutines(true)
        }.onSuccess { response ->
            uiState = uiState.copy(favouriteRoutines = response)
            val routines = uiState.routines
            for (routine in response) {
                if (routines != null) {
                    if (routine.id in routines.map { it.id }) {
                        uiState.routines?.find { it.id == routine.id }?.liked = true
                    }
                }
                uiState.favouriteRoutines?.find { it.id == routine.id }?.liked = true
            }
            uiState = uiState.copy(isFetchingRoutine = false)
        }.onFailure { e ->
            uiState =
                uiState.copy(isFetchingRoutine = false, fetchRoutineErrorStringId = handleError(e))
        }
    }

    fun getRoutinesOrderBy() = viewModelScope.launch {
        uiState = uiState.copy(isFetchingRoutine = true)
        when (uiState.orderBy) {
            0 -> getRoutinesOrderBy("date", "desc")
            1 -> getRoutinesOrderBy("date", "asc")
            2 -> getRoutinesOrderBy("score", "desc")
            3 -> getRoutinesOrderBy("score", "asc")
            4 -> getRoutinesOrderBy("difficulty", "desc")
            5 -> getRoutinesOrderBy("difficulty", "asc")
            6 -> getRoutinesOrderBy("category", "desc")
            7 -> getRoutinesOrderBy("category", "asc")
        }
    }

    fun getRoutinesOrderBy(index: Int, label: String) = viewModelScope.launch {
        uiState = uiState.copy(isFetchingRoutine = true, orderBy = index, labelOrderBy = label)
        getRoutinesOrderBy()
    }

    fun getRoutinesOrderBy(orderBy: String, direction: String) = viewModelScope.launch {
        uiState = uiState.copy(isFetchingRoutine = true, fetchRoutineErrorStringId = null)
        runCatching {
            routinesRepository.getRoutinesOrderBy(orderBy, direction)
        }.onSuccess { response ->
            uiState = uiState.copy(routines = response)
            getFavoriteRoutines().join()
            uiState = uiState.copy(isFetchingRoutine = false)
        }.onFailure { e ->
            uiState =
                uiState.copy(isFetchingRoutine = false, fetchRoutineErrorStringId = handleError(e))
        }
    }

    fun getExercisesByCycle(cycleId: Int) = runOnViewModelScope(
        {
            exercisesRepository.getExercisesByCycle(cycleId)
        },
        { state, response ->
            state.copy(exercises = response)
        }
    )

    /*
    fun getExerciseByCycle(cycleId: Int, exerciseId: Int) = runOnViewModelScope(
        {
            exercisesRepository.getExerciseByCycle(cycleId, exerciseId)
        },
        { state, response ->
            state.copy(currentExercise = response)
        }
    )
     */

    private fun addRoutineToFavorites(routineId: Int) = viewModelScope.launch {
        uiState = uiState.copy(isFetchingRoutine = true, fetchRoutineErrorStringId = null)
        runCatching {
            routinesRepository.addFavoriteRoutine(routineId)
        }.onSuccess { response ->
            for (routine in uiState.routines.orEmpty()) {
                if (routine.id == routineId) {
                    routine.liked = true
                    uiState =
                        uiState.copy(favouriteRoutines = uiState.favouriteRoutines?.plus(routine))
                    var routines = uiState.routines
                    if (routines != null) {
                        routines.find { it.id == routine.id }?.liked = true
                        uiState = uiState.copy(routines = routines)
                    }
                }
            }
            getRoutinesOrderBy().join()
            uiState = uiState.copy(isFetchingRoutine = false)
        }.onFailure { e ->
            uiState =
                uiState.copy(isFetchingRoutine = false, fetchRoutineErrorStringId = handleError(e))
        }
    }

    private fun removeRoutineFromFavorites(routineId: Int) = viewModelScope.launch {
        uiState = uiState.copy(isFetchingRoutine = true, fetchRoutineErrorStringId = null)
        runCatching {
            routinesRepository.removeFavoriteRoutine(routineId)
        }.onSuccess { response ->
            for (routine in uiState.routines.orEmpty()) {
                if (routine.id == routineId) {
                    routine.liked = false
                    uiState =
                        uiState.copy(favouriteRoutines = uiState.favouriteRoutines?.minus(routine))
                    var routines = uiState.routines
                    if (routines != null) {
                        routines.find { it.id == routine.id }?.liked = false
                        uiState = uiState.copy(routines = routines)
                    }
                }
            }
            getRoutinesOrderBy().join()
            uiState = uiState.copy(isFetchingRoutine = false)
        }.onFailure { e ->
            uiState =
                uiState.copy(isFetchingRoutine = false, fetchRoutineErrorStringId = handleError(e))
        }
    }

    fun toggleLike(routine: Routine) = viewModelScope.launch {
        uiState = uiState.copy(isFetchingRoutine = true, fetchRoutineErrorStringId = null)
        uiState = uiState.copy(updatedFavs = true)
        if (routine.liked) {
            routine.liked = false
            removeRoutineFromFavorites(routine.id)
        } else {
            routine.liked = true
            addRoutineToFavorites(routine.id)
        }
    }

    fun getRoutine(routineId: Int) = viewModelScope.launch {
        uiState.cycleDetailList = emptyList()
        uiState = uiState.copy(
            isFetchingR = true,
            fetchRoutineErrorStringId = null
        )
        runCatching {
            routinesRepository.getRoutine(routineId)
        }.onSuccess { rout ->
            uiState = uiState.copy(currentRoutine = rout)
            getRoutinesCycles(routineId).join()

            for (cycle in uiState.routineCycles) {
                getExercisesByCycle(cycle.id).join()
                uiState = uiState.copy(
                    cycleDetailList = uiState.cycleDetailList.plus(
                        CyclesDetail(
                            exercises = uiState.exercises,
                            cycle
                        )
                    )
                )
            }
            uiState = uiState.copy(isFetchingR = false)
        }.onFailure { e ->
            uiState = uiState.copy(isFetchingR = false, fetchRoutineErrorStringId = handleError(e))
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

    fun nextExercise() = viewModelScope.launch {
        uiState = uiState.copy(isFetchingRoutine = true, fetchRoutineErrorStringId = null)
        if (uiState.currentCycleIndex == uiState.cycleDetailList.size - 1 && uiState.currentExerciseIndex == uiState.cycleDetailList.last().exercises.size - 1) {
            uiState = uiState.copy(isExecuting = false)
        }
        if (uiState.currentExerciseIndex < uiState.cycleDetailList[uiState.currentCycleIndex].exercises.size - 1) {
            uiState = uiState.copy(currentExerciseIndex = uiState.currentExerciseIndex + 1)
            uiState =
                uiState.copy(currentExercise = uiState.cycleDetailList[uiState.currentCycleIndex].exercises[uiState.currentExerciseIndex])
        } else {
            uiState = uiState.copy(currentExerciseIndex = 0)
            if (uiState.currentCycleIndex < uiState.cycleDetailList.size - 1) {
                uiState = uiState.copy(currentCycleIndex = uiState.currentCycleIndex + 1)
                uiState =
                    uiState.copy(currentRoutineCycle = uiState.cycleDetailList[uiState.currentCycleIndex].cycle)
            } else {
                uiState = uiState.copy(currentCycleIndex = 0)
                uiState =
                    uiState.copy(currentRoutineCycle = uiState.cycleDetailList[uiState.currentCycleIndex].cycle)
            }
        }
        uiState = uiState.copy(isFetchingRoutine = false)
    }

    fun previousExercise() = viewModelScope.launch {
        uiState = uiState.copy(isFetchingRoutine = true, fetchRoutineErrorStringId = null)
        if (uiState.currentExerciseIndex > 0) {
            uiState = uiState.copy(currentExerciseIndex = uiState.currentExerciseIndex - 1)
            uiState =
                uiState.copy(currentExercise = uiState.cycleDetailList[uiState.currentCycleIndex].exercises[uiState.currentExerciseIndex])
        } else {
            uiState =
                uiState.copy(currentExerciseIndex = uiState.cycleDetailList[uiState.currentCycleIndex].exercises.size - 1)
            if (uiState.currentCycleIndex > 0) {
                uiState = uiState.copy(currentCycleIndex = uiState.currentCycleIndex - 1)
                uiState =
                    uiState.copy(currentRoutineCycle = uiState.cycleDetailList[uiState.currentCycleIndex].cycle)
            } else {
                uiState = uiState.copy(currentCycleIndex = uiState.routineCycles.size - 1)
                uiState =
                    uiState.copy(currentRoutineCycle = uiState.cycleDetailList[uiState.currentCycleIndex].cycle)

            }
        }
        uiState = uiState.copy(isFetchingRoutine = false)
    }

    fun startExecution(routineId: Int) = viewModelScope.launch {
        uiState = uiState.copy(isFetchingExecution = true, fetchRoutineErrorStringId = null)
        if (!uiState.aux) {
            uiState = uiState.copy(aux = true)
            getRoutine(routineId).join()
            uiState = uiState.copy(isExecuting = true)
            uiState = uiState.copy(currentRoutineCycle = uiState.cycleDetailList.first().cycle)
            uiState =
                uiState.copy(currentExercise = uiState.cycleDetailList.first().exercises.first())
            uiState = uiState.copy(currentCycleIndex = 0)
            uiState = uiState.copy(currentExerciseIndex = 0)
        }
        uiState = uiState.copy(isFetchingRoutine = false, isFetchingExecution = false)
    }

    fun setReview(routineId: Int, review: Review) = runOnViewModelScope(
        {
            routinesRepository.setReview(routineId, review)
        },
        { state, _ -> state }
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