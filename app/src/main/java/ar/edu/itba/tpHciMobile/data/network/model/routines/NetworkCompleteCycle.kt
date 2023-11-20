package ar.edu.itba.tpHciMobile.data.network.model.routines

import com.google.gson.annotations.SerializedName
import ar.edu.itba.tpHciMobile.data.model.CompleteCycle
import ar.edu.itba.tpHciMobile.data.model.Exercises
import ar.edu.itba.tpHciMobile.data.network.model.exercises.NetworkExercise

class NetworkCompleteCycle (
    @SerializedName("id"          ) var id          : Int,
    @SerializedName("type"        ) var type        : String,
    @SerializedName("order"       ) var order       : Int,
    @SerializedName("name"        ) var name        : String,
    @SerializedName("detail"      ) var detail      : String? = null,
    @SerializedName("repetitions" ) var repetitions : Int,
    @SerializedName("exercises"   ) var exercises   : List<NetworkExercise> = emptyList(),
    @SerializedName("metadata") var metadata : String? = null
) {
    fun asModel() : CompleteCycle {
        val toReturn = mutableListOf<Exercises>()
        for (exercise in exercises) {
            toReturn.add(exercise.asModel())
        }
        return CompleteCycle(
            id = id,
            type = type,
            order = order,
            detail = detail,
            name = name,
            repetitions = repetitions,
            exercises = toReturn,
            metadata = metadata

        )
    }
}