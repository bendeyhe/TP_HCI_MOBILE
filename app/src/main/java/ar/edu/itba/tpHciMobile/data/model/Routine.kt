package ar.edu.itba.tpHciMobile.data.model

import ar.edu.itba.tpHciMobile.data.network.model.NetworkCategory
import ar.edu.itba.tpHciMobile.data.network.model.NetworkUser
import ar.edu.itba.tpHciMobile.data.network.model.Routines.NetworkRoutine
import java.util.Date

class Routine (
    var id: Int,
    var name: String,
    var detail: String? = null,
    var date: Date? = null,
    var score: Int? = null,
    var difficulty: String? = null,
    var user: NetworkUser? = null,
    var category: NetworkCategory,
    var liked: Boolean = false,
    var fromCUser: Boolean = false
) {
    fun asNetworkModel(): NetworkRoutine{
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
}