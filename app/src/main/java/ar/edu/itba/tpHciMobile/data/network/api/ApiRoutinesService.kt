package ar.edu.itba.tpHciMobile.data.network.api

import ar.edu.itba.tpHciMobile.data.model.Review
import ar.edu.itba.tpHciMobile.data.network.model.util.NetworkPagedContent
import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkCompleteCycle
import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkReview
import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkRoutine
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.Query

interface ApiRoutinesService {

    @GET("routines")
    suspend fun getRoutines(@Query("page") page: Int): Response<NetworkPagedContent<NetworkRoutine>>

    @GET("favourites")
    suspend fun getFavRoutines(@Query("page") page: Int): Response<NetworkPagedContent<NetworkRoutine>>

    @POST("favourites/{routineId}")
    suspend fun addFavRoutine(@Path("routineId") routineId: Int): Response<Unit>

    @DELETE("favourites/{routineId}")
    suspend fun removeFavRoutine(@Path("routineId") routineId: Int): Response<Unit>

    @GET("routines")
    suspend fun getRoutinesOrderBy(
        @Query("page") page: Int,
        @Query("orderBy") orderBy: String,
        @Query("direction") direction: String
    ): Response<NetworkPagedContent<NetworkRoutine>>

    @GET("routines/{routineId}")
    suspend fun getRoutine(@Path("routineId") routineId: Int): Response<NetworkRoutine>

    @GET("routines/{routineId}/cycles")
    suspend fun getCycles(@Path("routineId") routineId: Int): Response<NetworkPagedContent<NetworkCompleteCycle>>

    @GET("routines/{routineId}/cycles/{cycleId}")
    suspend fun getCycle(
        @Path("routineId") routineId: Int,
        @Path("cycleId") cycleId: Int
    ): Response<NetworkCompleteCycle>

    @POST("reviews/{routineId}")
    suspend fun setReview(@Path("routineId") routineId: Int, @Body review: Review): Response<Unit>

}