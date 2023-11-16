package ar.edu.itba.tpHciMobile.data.api

import ar.edu.itba.tpHciMobile.data.model.NetworkCredentials

import retrofit2.Call
import retrofit2.http.POST

interface ApiSportService {
    @POST("login")
    fun login(credentials: NetworkCredentials): Call<String>

}