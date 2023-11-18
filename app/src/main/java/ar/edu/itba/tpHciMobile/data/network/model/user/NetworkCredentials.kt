package ar.edu.itba.tpHciMobile.data.network.model.user

import com.google.gson.annotations.SerializedName

data class NetworkCredentials(
    @SerializedName("username")
    var username: String,
    @SerializedName("password")
    var password: String
)