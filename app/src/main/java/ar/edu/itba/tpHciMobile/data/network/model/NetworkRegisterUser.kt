package ar.edu.itba.tpHciMobile.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.Date

class NetworkRegisterUser(
    @SerializedName("username")
    var username: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("gender")
    var gender: String? = null,
    @SerializedName("birthdate")
    var birthdate: Date? = null,
    @SerializedName("email")
    var email: String,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("avatarUrl")
    var avatarUrl: String? = null,
    @SerializedName("metadata")
    var metadata: NetworkUserMetadata? = null
)