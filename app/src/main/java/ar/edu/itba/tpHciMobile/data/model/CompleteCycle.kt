package ar.edu.itba.tpHciMobile.data.model

import ar.edu.itba.tpHciMobile.data.network.model.Routines.NetworkCompleteCycle

class CompleteCycle (
    var id: Int,
    var type: String,
    var order: Int,
    var detail: String? = null,
    var name: String,
    var repetitions: Int
) {
    fun asModel() : NetworkCompleteCycle {
        return NetworkCompleteCycle(
            id = id,
            type = type,
            order = order,
            detail = detail,
            name = name,
            repetitions = repetitions
        )
    }
}