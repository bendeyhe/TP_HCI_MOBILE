package ar.edu.itba.tpHciMobile.data.network.api

import ar.edu.itba.tpHciMobile.data.network.model.exercises.NetworkExercise
import ar.edu.itba.tpHciMobile.data.network.model.util.NetworkPagedContent
import retrofit2.Response
import retrofit2.http.*

interface ApiExercisesService {
    @GET("exercises")
    suspend fun getExercises(@Query("page") page: Int): Response<NetworkPagedContent<NetworkExercise>>

    @GET("exercises/{exerciseId}")
    suspend fun getExercise(@Path("exerciseId") exerciseId: Int) : Response<NetworkExercise>
}