package ar.edu.itba.tpHciMobile.data.network.model.exercises

import ar.edu.itba.tpHciMobile.data.model.Exercises
import com.google.gson.annotations.SerializedName
import java.util.Date

class NetworkExercise(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("detail")
    var detail: String? = null,
    @SerializedName("type")
    var type: String,
    @SerializedName("date")
    var date: Date? = null,
    @SerializedName("metadata")
    var metadata: String? = null
) {
    fun asModel(): Exercises {
        return Exercises(
            id = id,
            name = name,
            detail = detail,
            type = type,
        )
    }
}