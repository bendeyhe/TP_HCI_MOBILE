package ar.edu.itba.tpHciMobile.ui.components

import ar.edu.itba.tpHciMobile.R
sealed class FilterOptions (
    val text: Int,
    val order: String,
    val dir: String
){
    object  DateUp: FilterOptions(
        text = R.string.order_by_date_asc,
        order = "date",
        dir = "asc"
    )
    object  DateDown: FilterOptions(
        text = R.string.order_by_date_desc,
        order = "date",
        dir = "desc"
    )
    object RatingUp: FilterOptions(
        text = R.string.order_by_rating_asc,
        order = "score",
        dir = "asc"
    )
    object RatingDown: FilterOptions(
        text = R.string.order_by_rating_desc,
        order = "score",
        dir = "desc"
    )
    object  DifficultyUp: FilterOptions(
        text = R.string.order_by_diff_asc,
        order = "difficulty",
        dir = "asc"
    )
    object  DifficultyDown: FilterOptions(
        text = R.string.order_by_diff_desc,
        order = "difficulty",
        dir = "desc"
    )
}