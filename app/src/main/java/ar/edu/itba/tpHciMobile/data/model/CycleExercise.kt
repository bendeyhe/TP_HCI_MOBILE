package ar.edu.itba.tpHciMobile.data.model

import ar.edu.itba.tpHciMobile.data.network.model.exercises.NetworkCycleExercise

class CycleExercise(
    var duration: Int? = null,
    var exercise: Exercises,
    var order: Int,
    var metadata: String? = null,
    var repetitions: Int? = null
) {
    fun asNetworkModel(): NetworkCycleExercise {
        return NetworkCycleExercise(
            repetitions = repetitions,
            order = order,
            exercise = exercise.asModel(),
            duration = duration,
            metadata = metadata
        )
    }
}