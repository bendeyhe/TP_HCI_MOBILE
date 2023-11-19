package ar.edu.itba.tpHciMobile.ui.main

import android.app.Application
import ar.edu.itba.tpHciMobile.data.network.datasources.SportRemoteDataSource
import ar.edu.itba.tpHciMobile.data.network.datasources.UserRemoteDataSource
import ar.edu.itba.tpHciMobile.data.network.api.RetrofitClient
import ar.edu.itba.tpHciMobile.data.network.datasources.RoutinesRemoteDataSource
import ar.edu.itba.tpHciMobile.data.repository.RoutinesCycleRepository
import ar.edu.itba.tpHciMobile.data.repository.RoutinesRepository
import ar.edu.itba.tpHciMobile.data.repository.SportRepository
import ar.edu.itba.tpHciMobile.data.repository.UserRepository
import ar.edu.itba.tpHciMobile.util.SessionManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private val userRemoteDataSource: UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager, RetrofitClient.getApiUserService(this))

    private val sportRemoteDataSource: SportRemoteDataSource
        get() = SportRemoteDataSource(RetrofitClient.getApiSportService(this))

    private val routinesRemoteDataSource: RoutinesRemoteDataSource
        get() = RoutinesRemoteDataSource(RetrofitClient.getApiRoutinesService(this))

    val sessionManager: SessionManager
        get() = SessionManager(this)

    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)

    val sportRepository: SportRepository
        get() = SportRepository(sportRemoteDataSource)

    val routinesRepository: RoutinesRepository
        get() = RoutinesRepository(routinesRemoteDataSource)

    val routinesCycleRepository: RoutinesCycleRepository
        get() = RoutinesCycleRepository(routinesRemoteDataSource)

    companion object {
            lateinit var instance: MyApplication
                private set
    }
}