package ar.edu.itba.tpHciMobile.data

import ar.edu.itba.tpHciMobile.data.model.NetworkListUsers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/users")
    suspend fun getAllUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 50
    ): Response<NetworkListUsers>
}