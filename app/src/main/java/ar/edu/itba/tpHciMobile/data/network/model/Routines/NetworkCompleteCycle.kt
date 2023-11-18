package ar.edu.itba.tpHciMobile.data.network.model.Routines

import com.google.gson.annotations.SerializedName

class NetworkCompleteCycle (
    @SerializedName("id"          ) var id          : Int,
    @SerializedName("type"        ) var type        : String,
    @SerializedName("order"       ) var order       : Int,
    @SerializedName("name"        ) var name        : String,
    @SerializedName("detail"      ) var detail      : String? = null,
    @SerializedName("repetitions" ) var repetitions : Int,
    @SerializedName("metadata"    ) var metadata    : String? = null
)