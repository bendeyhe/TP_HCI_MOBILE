package ar.edu.itba.tpHciMobile.data.network.model.util

import ar.edu.itba.tpHciMobile.data.model.Sport
import com.google.gson.annotations.SerializedName

data class NetworkSport(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String,
    @SerializedName("detail")
    var detail: String? = null
) {
    fun asModel(): Sport {
        return Sport(
            id = id,
            name = name,
            detail = detail
        )
    }
}