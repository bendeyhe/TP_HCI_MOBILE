package ar.edu.itba.tpHciMobile.data.repository

import ar.edu.itba.tpHciMobile.data.model.Routine
import ar.edu.itba.tpHciMobile.data.model.User
import ar.edu.itba.tpHciMobile.data.network.datasources.UserRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserRepository(
    private val remoteDataSource: UserRemoteDataSource
) {

    // Mutex to make writes to cached values thread-safe.
    private val userMutex = Mutex()

    // Cache of the current user got from the network.
    private var currentUser: User? = null

    // Cache of the current user routines got from the network.
    private var userRoutines: List<Routine> = emptyList()

    suspend fun register(user: User) : User {
        return remoteDataSource.register(user.asNetworkModel()).asModel()
    }

    suspend fun resendVerification(email: String) {
        remoteDataSource.resendVerification(email)
    }

    suspend fun verifyEmail(email: String, code: String) {
        remoteDataSource.verifyEmail(email, code)
    }

    suspend fun login(username: String, password: String) {
        remoteDataSource.login(username, password)
    }

    suspend fun logout() {
        remoteDataSource.logout()
    }

    suspend fun updateCurrentUser(user: User) : User {
        return remoteDataSource.updateCurrentUser(user.asNetworkModel()).asModel()
    }

    suspend fun getCurrentUserRoutines(refresh: Boolean = false): List<Routine> {
        var page = 0
        if (refresh || userRoutines.isEmpty()) {
            this.userRoutines = emptyList()
            do {
                val result = remoteDataSource.getCurrentUserRoutines(page)
                userMutex.withLock {
                    this.userRoutines = this.userRoutines.plus(result.content.map { it.asModel() })
                }
                page++
            } while (!result.isLastPage)
        }
        return userMutex.withLock { this.userRoutines }
    }

    suspend fun getCurrentUser(refresh: Boolean): User? {
        if (refresh || currentUser == null) {
            val result = remoteDataSource.getCurrentUser()
            // Thread-safe write to latestNews
            userMutex.withLock {
                this.currentUser = result.asModel()
            }
        }
        return userMutex.withLock { this.currentUser }
    }
}