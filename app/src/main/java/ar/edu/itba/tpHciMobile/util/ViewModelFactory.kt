package ar.edu.itba.tpHciMobile.util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.MainViewModel
import ar.edu.itba.tpHciMobile.data.repository.SportRepository
import ar.edu.itba.tpHciMobile.data.repository.UserRepository
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.UserViewModel

class ViewModelFactory constructor(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val sportRepository: SportRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(sessionManager, userRepository, sportRepository)

            isAssignableFrom(UserViewModel::class.java) ->
                UserViewModel(sessionManager, userRepository)

            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}