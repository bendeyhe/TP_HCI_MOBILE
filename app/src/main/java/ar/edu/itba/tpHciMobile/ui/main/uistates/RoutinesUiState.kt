package ar.edu.itba.tpHciMobile.ui.main.uistates

import ar.edu.itba.tpHciMobile.data.model.CompleteCycle
import ar.edu.itba.tpHciMobile.data.model.Error
import ar.edu.itba.tpHciMobile.data.model.Exercises
import ar.edu.itba.tpHciMobile.data.model.Routine
import ar.edu.itba.tpHciMobile.ui.components.FilterOptions
import ar.edu.itba.tpHciMobile.ui.model.CyclesDetail


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
    val routineCycles: List<CompleteCycle> = emptyList(),
    val currentRoutineCycle: CompleteCycle? = null,
    val exercises: List<Exercises>? = null,
    val currentExercise: Exercises? = null,


    var cycleDetailList: List<CyclesDetail> = emptyList(),

    val favouriteRoutines: List<Routine>? = null,

    val isFetchingR: Boolean = false,

    val userRoutines: List<Routine>? = null,
    val isFetchingRoutine: Boolean = false,
    val fetchingRoutineId: Int? = null,
    val fetchRoutineErrorStringId: Error? = null,
)