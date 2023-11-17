package ar.edu.itba.tpHciMobile

import ar.edu.itba.tpHciMobile.data.model.NetworkListUsers

data class MainUiState(
    val users: NetworkListUsers? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

val MainUiState.hasError: Boolean get() = message != null