package ar.edu.itba.tpHciMobile.data.network.model

import com.google.gson.annotations.SerializedName

class NetworkCategory (

    @SerializedName("id"     ) var id     : Int?    = null,
    @SerializedName("name"   ) var name   : String,
    @SerializedName("detail" ) var detail : String

)