package ar.edu.itba.tpHciMobile.data.model

class Sport(
    id: Int,
    name: String,
    description: String,
    image: String,
    active: Boolean,
    deleted: Boolean,
    createdAt: String,
    updatedAt: String,
    deletedAt: String
) {
    fun asNetworkModel(): NetworkSport {
        return NetworkSport(
            id,
            name,
            description,
            image,
            active,
            deleted,
            createdAt,
            updatedAt,
            deletedAt
        )
    }

    var id: Int = 0
    var name: String = ""
    var description: String = ""
    var image: String = ""
    var active: Boolean = false
    var deleted: Boolean = false
    var createdAt: String = ""
    var updatedAt: String = ""
    var deletedAt: String = ""
}