package ar.edu.itba.tpHciMobile.data.network.model.exercises

import ar.edu.itba.tpHciMobile.data.model.CycleExercise
import com.google.gson.annotations.SerializedName

class NetworkCycleExercise(
    @SerializedName("exercise") var exercise: NetworkExercise,
    @SerializedName("repetitions") var repetitions: Int? = null,
    @SerializedName("order") var order: Int,
    @SerializedName("metadata") var metadata: String? = null,
    @SerializedName("duration") var duration: Int? = null
) {
    fun asModel(): CycleExercise {
        return CycleExercise(
            duration = duration,
            exercise = exercise.asModel(),
            order = order,
            metadata = metadata,
            repetitions = repetitions
        )
    }
}