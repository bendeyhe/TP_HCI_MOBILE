package ar.edu.itba.tpHciMobile.data.network.model.util

import com.google.gson.annotations.SerializedName

data class NetworkError(
    @SerializedName("code")
    val code: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("details")
    val details: List<String>? = null
)