package ar.edu.itba.tpHciMobile.data.model

import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkReview
import ar.edu.itba.tpHciMobile.data.network.model.user.NetworkUser
import java.util.Date

class Review(
    var score: Int,
    var review: String = "",
) /*{
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
    */