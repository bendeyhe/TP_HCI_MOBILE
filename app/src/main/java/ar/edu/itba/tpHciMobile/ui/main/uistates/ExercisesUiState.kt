package ar.edu.itba.tpHciMobile.ui.main.uistates

import ar.edu.itba.tpHciMobile.data.model.Exercises
import ar.edu.itba.tpHciMobile.data.model.Error

data class ExercisesUiState (
    val exercises: List<Exercises>? = null,
    val currentExercise: Exercises? = null,
    val isFetchingExercise: Boolean = false,
    val errorFetchingMessage: Error? = null,
)