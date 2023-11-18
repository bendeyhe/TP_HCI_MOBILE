package ar.edu.itba.tpHciMobile.data.network.model.user

import com.google.gson.annotations.SerializedName

data class NetworkVerifyUser(
    @SerializedName("email")
    var username: String,
    @SerializedName("code")
    var password: String
)