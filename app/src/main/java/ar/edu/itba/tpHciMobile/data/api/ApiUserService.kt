package ar.edu.itba.tpHciMobile.data.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiUserService {
    @GET("sports")
    fun getSports(): Call<String>

    @GET("sports/{id}")
    fun getSport(id: Int): Call<String>
}