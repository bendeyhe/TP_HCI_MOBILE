package ar.edu.itba.tpHciMobile.data.model

import com.google.gson.annotations.SerializedName

class NetworkSupport (
    @SerializedName("url")
    var url: String,
    @SerializedName("text")
    var text: String
)