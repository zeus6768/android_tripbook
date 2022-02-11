package com.olympos.tripbook.src.home.model

import retrofit2.Call
import retrofit2.http.*

interface HomeRetrofitInterface {
    @GET("/app/trips/count/{userIdx}")
    fun getTripCount(@Path("userIdx") userIdx: Int? = null): Call<HomeResponse>
}