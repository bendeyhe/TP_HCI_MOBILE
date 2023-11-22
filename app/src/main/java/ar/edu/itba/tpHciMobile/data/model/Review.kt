package ar.edu.itba.tpHciMobile.data.model

import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkReview
import ar.edu.itba.tpHciMobile.data.network.model.user.NetworkUser
import java.util.Date

class Review (
    var id: Int = 0,
    var date: Date? = null,
    var score: Int? = null,
    var review: String = "",
    var user: NetworkUser? = null
    ) {
        fun asModel(): NetworkReview {
            return NetworkReview(
                id = id,
                date = date,
                score = score,
                review = review,
                user = user
            )
        }
    }