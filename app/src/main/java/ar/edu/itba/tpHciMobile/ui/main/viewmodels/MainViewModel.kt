package ar.edu.itba.tpHciMobile.ui.main.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.tpHciMobile.data.DataSourceException
import ar.edu.itba.tpHciMobile.data.model.Error
import ar.edu.itba.tpHciMobile.data.model.Sport
import ar.edu.itba.tpHciMobile.data.repository.SportRepository
import ar.edu.itba.tpHciMobile.data.repository.UserRepository
import ar.edu.itba.tpHciMobile.ui.main.uistates.MainUiState
import ar.edu.itba.tpHciMobile.util.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
    sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val sportRepository: SportRepository
) : ViewModel() {

    var uiState by mutableStateOf(MainUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set

    fun login(username: String, password: String) = runOnViewModelScope(
        { userRepository.login(username, password) },
        { state, _ -> state.copy(isAuthenticated = true) }
    )

    fun logout() = runOnViewModelScope(
        { userRepository.logout() },
        { state, _ ->
            state.copy(
                isAuthenticated = false,
                currentUser = null,
                currentSport = null,
                sports = null
            )
        }
    )

    fun getCurrentUser() = runOnViewModelScope(
        { userRepository.getCurrentUser(uiState.currentUser == null) },
        { state, response -> state.copy(currentUser = response) }
    )

    fun getSports() = runOnViewModelScope(
        { sportRepository.getSports(true) },
        { state, response -> state.copy(sports = response) }
    )

    fun getSport(sportId: Int) = runOnViewModelScope(
        { sportRepository.getSport(sportId) },
        { state, response -> state.copy(currentSport = response) }
    )

    fun addOrModifySport(sport: Sport) = runOnViewModelScope(
        {
            if (sport.id == null)
                sportRepository.addSport(sport)
            else
                sportRepository.modifySport(sport)
        },
        { state, response ->
            state.copy(
                currentSport = response,
                sports = null
            )
        }
    )

    fun deleteSport(sportId: Int) = runOnViewModelScope(
        { sportRepository.deleteSport(sportId) },
        { state, response ->
            state.copy(
                currentSport = null,
                sports = null
            )
        }
    )

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (MainUiState, R) -> MainUiState
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