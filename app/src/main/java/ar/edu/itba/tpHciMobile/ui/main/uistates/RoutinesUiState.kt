package ar.edu.itba.tpHciMobile.ui.main.uistates

import ar.edu.itba.tpHciMobile.data.model.CompleteCycle
import ar.edu.itba.tpHciMobile.data.model.Error
import ar.edu.itba.tpHciMobile.data.model.Routine
import ar.edu.itba.tpHciMobile.ui.components.FilterOptions

data class RoutinesUiState (
    val routines: List<Routine>? = null,
    val currentRoutine: Routine? = null,
    val orderBy: Int = 0,
    val filters: List<FilterOptions>? = listOf(
        FilterOptions.DateUp,
        FilterOptions.DateDown,
        FilterOptions.RatingUp,
        FilterOptions.RatingDown,
        FilterOptions.DifficultyUp,
        FilterOptions.DifficultyDown,
    ),
    val routinesCycles: List<CompleteCycle> = emptyList(),
    val currentRoutineCycle: CompleteCycle? = null,

    val favouriteRoutines: List<Routine>? = null,

    //val favouriteRoutines: List<Routine>? = null,

    val userRoutines: List<Routine>? = null,
    val isFetchingRoutine: Boolean = false,
    val fetchingRoutineId: Int? = null,
    val fetchRoutineErrorStringId: Error? = null,
)