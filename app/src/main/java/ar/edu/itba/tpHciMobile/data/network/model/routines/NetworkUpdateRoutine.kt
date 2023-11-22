package ar.edu.itba.tpHciMobile.data.network.model.routines

import com.google.gson.annotations.SerializedName

class NetworkUpdateRoutine (
    @SerializedName("name") var name: String? = null,
    @SerializedName("detail") var detail: String? = null,
    @SerializedName("isPublic") var isPublic: Boolean? = null,
    @SerializedName("difficulty") var difficulty: String? = null,
    @SerializedName("category") var category: Int? = null,
    @SerializedName("metadata") var metadata: String? = null
    )