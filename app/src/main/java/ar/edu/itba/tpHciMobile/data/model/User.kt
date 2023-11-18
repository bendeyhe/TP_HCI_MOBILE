package ar.edu.itba.tpHciMobile.data.model

import ar.edu.itba.tpHciMobile.data.network.model.user.NetworkUser
import java.util.Date

data class User(
    var id: Int? = null,
    var username: String? = null,
    var password: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var gender: String? = null,
    var birthdate: Date? = null,
    var email: String? = null,
    var phone: String? = null,
    var avatarUrl: String? = null,
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
