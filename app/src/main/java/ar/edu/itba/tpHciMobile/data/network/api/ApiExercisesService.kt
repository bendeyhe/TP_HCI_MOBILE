package ar.edu.itba.tpHciMobile.data.network.api

import ar.edu.itba.tpHciMobile.data.network.model.exercises.NetworkCycleExercise
import ar.edu.itba.tpHciMobile.data.network.model.exercises.NetworkExercise
import ar.edu.itba.tpHciMobile.data.network.model.util.NetworkPagedContent
import retrofit2.Response
import retrofit2.http.*

interface ApiExercisesService {
    @GET("exercises")
    suspend fun getExercises(@Query("page") page: Int): Response<NetworkPagedContent<NetworkExercise>>

    @GET("exercises/{exerciseId}")
    suspend fun getExercise(@Path("exerciseId") exerciseId: Int): Response<NetworkExercise>

    @GET("cycles/{cycleId}/exercises")
    suspend fun getExercisesByCycle(
        @Path("cycleId") cycleId: Int,
        @Query("page") page: Int
    ): Response<NetworkPagedContent<NetworkCycleExercise>>

    @GET("cycles/{cycleId}/exercises/{exerciseId}")
    suspend fun getExerciseByCycle(
        @Path("cycleId") cycleId: Int,
        @Path("exerciseId") exerciseId: Int
    ): Response<NetworkExercise>
}