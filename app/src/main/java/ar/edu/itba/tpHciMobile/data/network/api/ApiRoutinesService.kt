package ar.edu.itba.tpHciMobile.data.network.api

import ar.edu.itba.tpHciMobile.data.network.model.util.NetworkPagedContent
import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkCompleteCycle
import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkRoutine
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.Query

interface ApiRoutinesService {

    @GET("routines")
    suspend fun getRoutines(@Query("page") page: Int): Response<NetworkPagedContent<NetworkRoutine>>

    @GET("routines")
    suspend fun getRoutinesOrderBy(@Query("page") page: Int, @Query("orderBy") orderBy: String, @Query("direction") direction: String): Response<NetworkPagedContent<NetworkRoutine>>

    @GET("routines/{routineId}")
    suspend fun getRoutine(@Path("routineId") routineId: Int) : Response<NetworkRoutine>

    @GET("routines/{routineId}/cycles")
    suspend fun getCycles(@Path("routineId") routineId: Int, @Query("page") page: Int) : Response<NetworkPagedContent<NetworkCompleteCycle>>

    @GET("routines/{routineId}/cycles/{cycleId}")
    suspend fun getCycle(@Path("routineId") routineId: Int, @Path("cycleId") cycleId: Int) : Response<NetworkCompleteCycle>

}