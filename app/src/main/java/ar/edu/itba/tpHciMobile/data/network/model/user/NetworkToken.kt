package ar.edu.itba.tpHciMobile.data.network.model.user

import com.google.gson.annotations.SerializedName

data class NetworkToken(
    @SerializedName("token")
    var token: String
)