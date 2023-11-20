package ar.edu.itba.tpHciMobile.util

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import ar.edu.itba.tpHciMobile.ui.main.MyApplication

@Composable
fun getViewModelFactory(defaultArgs: Bundle? = null): ViewModelFactory {
    val application = (LocalContext.current.applicationContext as MyApplication)
    val sessionManager = application.sessionManager
    val userRepository = application.userRepository
    val sportRepository = application.sportRepository
    val routinesRepository = application.routinesRepository
    val routinesCycleRepository = application.routinesCycleRepository
    val exercisesRepository = application.exercisesRepository
    return ViewModelFactory(
        sessionManager,
        userRepository,
        sportRepository,
        routinesRepository,
        routinesCycleRepository,
        exercisesRepository,
        LocalSavedStateRegistryOwner.current,
        defaultArgs
    )
}