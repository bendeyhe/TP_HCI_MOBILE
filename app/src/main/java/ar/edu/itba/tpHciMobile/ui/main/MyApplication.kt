package ar.edu.itba.tpHciMobile.ui.main

import android.app.Application
import ar.edu.itba.tpHciMobile.data.network.datasources.SportRemoteDataSource
import ar.edu.itba.tpHciMobile.data.network.datasources.UserRemoteDataSource
import ar.edu.itba.tpHciMobile.data.network.api.RetrofitClient
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

    val sessionManager: SessionManager
        get() = SessionManager(this)

    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)

    val sportRepository: SportRepository
        get() = SportRepository(sportRemoteDataSource)
    companion object {
            lateinit var instance: MyApplication
                private set
    }
}