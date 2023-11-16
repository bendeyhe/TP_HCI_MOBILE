package ar.edu.itba.tpHciMobile.data.model

class NetworkPagedContent(
    map: List<NetworkSport>,
    totalPages: Int,
    totalElements: Int,
    last: Boolean,
    size: Int,
    number: Int,
    first: Boolean,
    numberOfElements: Int,
    empty: Boolean
) {
    fun asModel(): PagedContent {
        return PagedContent(
            content,
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

    var content: List<NetworkSport> = emptyList()
    var totalPages: Int = 0
    var totalElements: Int = 0
    var last: Boolean = false
    var size: Int = 0
    var number: Int = 0
    var first: Boolean = false
    var numberOfElements: Int = 0
    var empty: Boolean = false
}