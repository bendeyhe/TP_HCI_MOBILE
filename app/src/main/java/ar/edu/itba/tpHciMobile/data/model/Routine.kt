package ar.edu.itba.tpHciMobile.data.model

import ar.edu.itba.tpHciMobile.data.network.model.util.NetworkCategory
import ar.edu.itba.tpHciMobile.data.network.model.user.NetworkUser
import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkRoutine
import java.util.Date

class Routine(
    var id: Int,
    var name: String,
    var detail: String? = null,
    var date: Date? = null,
    var score: Int = 0,
    var isPublic: Boolean? = null,
    var difficulty: String? = null,
    var user: NetworkUser? = null,
    var category: NetworkCategory,
    var liked: Boolean = false,
    var fromCUser: Boolean = false
) {
    fun asNetworkModel(): NetworkRoutine {
        return NetworkRoutine(
            id = id,
            name = name,
            detail = detail,
            date = date,
            score = score,
            difficulty = difficulty,
            user = user,
            category = category
        )
    }

    fun copy(liked: Boolean): Routine {
        return Routine(
            id = id,
            name = name,
            detail = detail,
            date = date,
            score = score,
            difficulty = difficulty,
            user = user,
            category = category,
            liked = liked,
            fromCUser = fromCUser
        )
    }
}