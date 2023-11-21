package ar.edu.itba.tpHciMobile.ui.main.uistates

import ar.edu.itba.tpHciMobile.data.model.Error
import ar.edu.itba.tpHciMobile.data.model.User

data class UserUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val currentUser: User? = null,
    val error: Error? = null
)

val UserUiState.canGetCurrentUser: Boolean get() = isAuthenticated
val UserUiState.canLogin: Boolean get() = !isAuthenticated
val UserUiState.canLogout: Boolean get() = isAuthenticated
val UserUiState.canRegister: Boolean get() = !isAuthenticated
val UserUiState.canModifyUser: Boolean get() = isAuthenticated && currentUser != null
