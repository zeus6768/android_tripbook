package com.olympos.tripbook.src.user.model

import retrofit2.Call
import retrofit2.http.GET

interface ApiTestRetrofitInterface {
    @GET("/app/test")
    fun getApiTest(): Call<ApiTestResponse>
}