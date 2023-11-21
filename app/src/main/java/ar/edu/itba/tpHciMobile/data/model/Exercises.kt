package ar.edu.itba.tpHciMobile.data.model

import ar.edu.itba.tpHciMobile.data.network.model.exercises.NetworkExercise
import java.util.Date

class Exercises(
    var id: Int,
    var name: String,
    var detail: String? = null,
    var type: String,
    var date: Date? = null,

    ) {
    fun asModel(): NetworkExercise {
        return NetworkExercise(
            id = id,
            name = name,
            detail = detail,
            type = type,
            date = date,
        )
    }
}