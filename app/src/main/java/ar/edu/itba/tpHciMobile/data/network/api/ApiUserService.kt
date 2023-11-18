package ar.edu.itba.tpHciMobile.data.network.api

import ar.edu.itba.tpHciMobile.data.network.model.util.NetworkPagedContent
import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkRoutine
import ar.edu.itba.tpHciMobile.data.network.model.user.NetworkCredentials
import ar.edu.itba.tpHciMobile.data.network.model.user.NetworkToken
import ar.edu.itba.tpHciMobile.data.network.model.user.NetworkUser
import ar.edu.itba.tpHciMobile.data.network.model.user.NetworkVerifyUser
import retrofit2.Response
import retrofit2.http.*

interface ApiUserService {
    @POST("users")
    suspend fun register(@Body user: NetworkUser): Response<NetworkUser>

    @POST("users/resend_verification")
    suspend fun resendVerification(@Body email: NetworkUser): Response<Unit>

    @POST("users/verify_email")
    suspend fun verifyEmail(@Body credentials: NetworkVerifyUser): Response<Unit>

    @POST("users/login")
    suspend fun login(@Body credentials: NetworkCredentials): Response<NetworkToken>

    @POST("users/logout")
    suspend fun logout(): Response<Unit>

    @PUT("users/current")
    suspend fun updateCurrentUser(@Body user: NetworkUser): Response<NetworkUser>

    @GET("users/current/routines")
    suspend fun getCurrentUserRoutines(@Query("page") page: Int): Response<NetworkPagedContent<NetworkRoutine>>

    @GET("users/current")
    suspend fun getCurrentUser(): Response<NetworkUser>
}
