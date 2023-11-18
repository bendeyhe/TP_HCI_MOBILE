package ar.edu.itba.tpHciMobile.data.model

import ar.edu.itba.tpHciMobile.data.network.model.user.NetworkUser
import java.util.Date

data class User(
    var id: Int?,
    var username: String?,
    var firstName: String?,
    var lastName: String?,
    var gender: String?,
    var birthdate: Date?,
    var email: String?,
    var phone: String?,
    var avatarUrl: String?,
    var lastActivity: Date? = null
) {
    fun asNetworkModel(): NetworkUser {
        return NetworkUser(
            id = id,
            username = username,
            firstName = firstName,
            lastName = lastName,
            gender = gender,
            birthdate = birthdate,
            email = email,
            phone = phone,
            avatarUrl = avatarUrl,
            lastActivity = lastActivity
        )
    }
}
