package com.olympos.tripbook.src.home.model

import retrofit2.Call
import retrofit2.http.*

interface HomeRetrofitInterface {
    @GET("/app/trips/count/{userIdx}")
    fun getTripCount(@Path("userIdx") userIdx: String): Call<HomeResponse>

    @GET("/app/trip/{tripIdx}/courses")
    fun getTrip(@Path("tripIdx") tripIdx : String) : Call<RecentTripResponse>
}