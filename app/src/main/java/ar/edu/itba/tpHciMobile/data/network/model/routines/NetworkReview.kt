package ar.edu.itba.tpHciMobile.data.network.model.routines

import ar.edu.itba.tpHciMobile.data.model.Review
import ar.edu.itba.tpHciMobile.data.model.Routine
import ar.edu.itba.tpHciMobile.data.network.model.user.NetworkUser
import ar.edu.itba.tpHciMobile.data.network.model.util.NetworkCategory
import com.google.gson.annotations.SerializedName
import java.util.Date

class NetworkReview(
    @SerializedName("id") var id: Int,
    @SerializedName("date") var date: Date? = null,
    @SerializedName("score") var score: Int,
    @SerializedName("review") var review: String,
    @SerializedName("user") var user: NetworkUser? = null
) {
    fun asModel(): Review {
        return Review(
            score = score,
            review = review,
        )
    }
}