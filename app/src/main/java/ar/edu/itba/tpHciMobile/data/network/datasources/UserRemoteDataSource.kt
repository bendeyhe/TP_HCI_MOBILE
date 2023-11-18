package ar.edu.itba.tpHciMobile.data.network.datasources

import ar.edu.itba.tpHciMobile.data.network.api.ApiUserService
import ar.edu.itba.tpHciMobile.data.network.model.util.NetworkPagedContent
import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkRoutine
import ar.edu.itba.tpHciMobile.data.network.model.user.NetworkCredentials
import ar.edu.itba.tpHciMobile.data.network.model.user.NetworkUser
import ar.edu.itba.tpHciMobile.data.network.model.user.NetworkVerifyUser
import ar.edu.itba.tpHciMobile.util.SessionManager


class UserRemoteDataSource(
    private val sessionManager: SessionManager,
    private val apiUserService: ApiUserService
) : RemoteDataSource() {

    suspend fun register(user: NetworkUser) {
        val response = handleApiResponse { apiUserService.register(user) }
        sessionManager.saveEmail(response.email) // todo no se si es necesario
    }

    suspend fun resendVerification(email: String) {
        val response = handleApiResponse { apiUserService.resendVerification(NetworkUser(email = email)) }
    }

    suspend fun verifyEmail(email: String, code: String) {
        val response = handleApiResponse { apiUserService.verifyEmail(NetworkVerifyUser(email, code)) }
        sessionManager.saveAuthToken(email) // todo no se si es necesario
    }

    suspend fun login(username: String, password: String) {
        val response = handleApiResponse {
            apiUserService.login(NetworkCredentials(username, password))
        }
        sessionManager.saveAuthToken(response.token)
    }

    suspend fun logout() {
        handleApiResponse { apiUserService.logout() }
        sessionManager.removeAuthToken()
    }

    suspend fun updateCurrentUser(user: NetworkUser) {
        val response = handleApiResponse { apiUserService.updateCurrentUser(user) }
    }

    suspend fun getCurrentUserRoutines(page: Int): NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse { apiUserService.getCurrentUserRoutines(page) }
    }

    suspend fun getCurrentUser(): NetworkUser {
        return handleApiResponse { apiUserService.getCurrentUser() }
    }
}