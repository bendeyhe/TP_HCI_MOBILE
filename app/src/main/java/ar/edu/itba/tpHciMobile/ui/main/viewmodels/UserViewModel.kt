package ar.edu.itba.tpHciMobile.ui.main.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.tpHciMobile.data.DataSourceException
import ar.edu.itba.tpHciMobile.data.model.Error
import ar.edu.itba.tpHciMobile.data.model.User
import ar.edu.itba.tpHciMobile.data.repository.UserRepository
import ar.edu.itba.tpHciMobile.ui.main.uistates.UserUiState
import ar.edu.itba.tpHciMobile.util.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserViewModel(
    sessionManager: SessionManager,
    private val userRepository: UserRepository,
) : ViewModel() {
    var uiState by mutableStateOf(UserUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set

    fun register(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String,
        gender: String
    ) = runOnViewModelScope(
        {
            userRepository.register(
                User(
                    username = username,
                    password = password,
                    email = email,
                    firstName = firstName,
                    lastName = lastName,
                    gender = gender
                )
            )
        },
        { state, _ -> state }
    )

    fun sendVerificationEmail(email: String) = runOnViewModelScope(
        { userRepository.resendVerification(email) },
        { state, _ -> state }
    )

    fun verifyEmail(email: String, code: String) = runOnViewModelScope(
        { userRepository.verifyEmail(email, code) },
        { state, _ -> state }
    )

    fun login(username: String, password: String): Boolean {
        runOnViewModelScope(
            {
                userRepository.login(username, password)
                userRepository.getCurrentUser(true)
            },
            { state, response -> state.copy(isAuthenticated = true, currentUser = response) }
        )
        return uiState.isAuthenticated
    }

    fun getCurrentUser() = runOnViewModelScope(
        { userRepository.getCurrentUser(true) },
        { state, response -> state.copy(currentUser = response) }
    )

    fun logout() = runOnViewModelScope(
        { userRepository.logout() },
        { state, _ ->
            state.copy(
                isAuthenticated = false,
                currentUser = null,
            )
        }
    )

    fun modifyCurrentUser(
        email: String,
        firstName: String,
        lastName: String,
        gender: String
    ) = runOnViewModelScope(
        {
            var user = uiState.currentUser
            user?.email = email
            user?.firstName = firstName
            user?.lastName = lastName
            user?.gender = gender
            userRepository.updateCurrentUser(user!!)
        },
        { state, response -> state.copy(currentUser = response) }
    )

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (UserUiState, R) -> UserUiState
    ): Job = viewModelScope.launch {
        uiState = uiState.copy(isFetching = true, error = null)
        runCatching {
            block()
        }.onSuccess { response ->
            uiState = updateState(uiState, response).copy(isFetching = false)
        }.onFailure { e ->
            uiState = uiState.copy(isFetching = false, error = handleError(e))
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }
}