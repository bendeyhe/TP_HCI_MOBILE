package ar.edu.itba.tpHciMobile.data.network.api

import ar.edu.itba.tpHciMobile.data.network.model.NetworkCredentials
import ar.edu.itba.tpHciMobile.data.network.model.NetworkToken
import ar.edu.itba.tpHciMobile.data.network.model.NetworkUser
import retrofit2.Response
import retrofit2.http.*

interface ApiUserService {
    @POST("users/login")
    suspend fun login(@Body credentials: NetworkCredentials): Response<NetworkToken>

    @POST("users/logout")
    suspend fun logout(): Response<Unit>

    @GET("users/current")
    suspend fun getCurrentUser(): Response<NetworkUser>
}
