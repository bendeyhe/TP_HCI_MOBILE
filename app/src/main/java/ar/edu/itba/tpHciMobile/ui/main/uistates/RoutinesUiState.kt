package ar.edu.itba.tpHciMobile.ui.main.uistates

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import ar.edu.itba.tpHciMobile.R
import ar.edu.itba.tpHciMobile.data.model.CompleteCycle
import ar.edu.itba.tpHciMobile.data.model.CycleExercise
import ar.edu.itba.tpHciMobile.data.model.Error
import ar.edu.itba.tpHciMobile.data.model.Exercises
import ar.edu.itba.tpHciMobile.data.model.Routine
import ar.edu.itba.tpHciMobile.ui.components.FilterOptions
import ar.edu.itba.tpHciMobile.ui.main.MyApplication
import ar.edu.itba.tpHciMobile.ui.model.CyclesDetail


data class RoutinesUiState(
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
    val exercises: List<CycleExercise> = emptyList(),
    val currentExercise: CycleExercise? = null,

    val currentCycleIndex: Int = 0,
    val currentExerciseIndex: Int = 0,

    var cycleDetailList: List<CyclesDetail> = emptyList(),

    val favouriteRoutines: List<Routine>? = null,

    val isFetchingR: Boolean = false,

    val userRoutines: List<Routine>? = null,
    val isFetchingRoutine: Boolean = false,
    val fetchingRoutineId: Int? = null,
    val fetchRoutineErrorStringId: Error? = null,
    val isFetchingExecution: Boolean = false,

    val labelOrderBy: String = MyApplication.instance.getString(R.string.order_by),

    val updatedFavs: Boolean = false,
    val isExecuting: Boolean = true,
    val aux: Boolean = false,

    val nextExercise: CycleExercise? = null,
)