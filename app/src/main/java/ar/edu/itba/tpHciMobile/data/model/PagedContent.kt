package ar.edu.itba.tpHciMobile.data.model

class PagedContent(
    content: List<NetworkSport>,
    totalPages: Int,
    totalElements: Int,
    last: Boolean,
    size: Int,
    number: Int,
    first: Boolean,
    numberOfElements: Int,
    empty: Boolean
) {
    fun asNetworkModel(): NetworkPagedContent {
        return NetworkPagedContent(
            content.map { it.asNetworkModel() },
            totalPages,
            totalElements,
            last,
            size,
            number,
            first,
            numberOfElements,
            empty
        )
    }

    var content: List<Sport> = emptyList()
    var totalPages: Int = 0
    var totalElements: Int = 0
    var last: Boolean = false
    var size: Int = 0
    var number: Int = 0
    var first: Boolean = false
    var numberOfElements: Int = 0
    var empty: Boolean = false
}