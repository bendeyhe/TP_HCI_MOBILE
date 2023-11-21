package ar.edu.itba.tpHciMobile.data.model

import ar.edu.itba.tpHciMobile.data.network.model.exercises.NetworkExercise
import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkCompleteCycle

class CompleteCycle(
    var id: Int,
    var type: String,
    var order: Int,
    var detail: String? = null,
    var name: String,
    var exercises: List<Exercises> = emptyList(),
    var repetitions: Int,
    var metadata: String? = null
) {
    fun asModel(): NetworkCompleteCycle {
        /*val toReturn = mutableListOf<NetworkExercise>()
        for (exercise in exercises) {
            toReturn.add(exercise.asModel())
        }

         */
        return NetworkCompleteCycle(
            id = id,
            type = type,
            order = order,
            detail = detail,
            name = name,
            repetitions = repetitions,
            //exercises = toReturn,
            metadata = metadata
        )
    }
}