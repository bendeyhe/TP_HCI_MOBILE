package ar.edu.itba.tpHciMobile.data.network.api

import ar.edu.itba.tpHciMobile.data.network.model.NetworkCredentials
import ar.edu.itba.tpHciMobile.data.network.model.NetworkRegisterUser
import ar.edu.itba.tpHciMobile.data.network.model.NetworkToken
import ar.edu.itba.tpHciMobile.data.network.model.NetworkUser
import retrofit2.Response
import retrofit2.http.*

interface ApiUserService {
    @POST("users/login")
    suspend fun login(@Body credentials: NetworkCredentials): Response<NetworkToken>

    @POST("users")
    suspend fun register(@Body user: NetworkRegisterUser): Response<NetworkUser>

    @POST("users/resend_verification")
    suspend fun resendVerification(@Body email: NetworkUser): Response<Void>

    @POST("users/logout")
    suspend fun logout(): Response<Unit>

    @GET("users/current")
    suspend fun getCurrentUser(): Response<NetworkUser>
}
