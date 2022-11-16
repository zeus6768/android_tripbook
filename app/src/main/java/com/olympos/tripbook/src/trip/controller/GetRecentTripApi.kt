package com.olympos.tripbook.src.trip.controller

import com.olympos.tripbook.src.trip.model.GetRecentTripResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetRecentTripApi {
    @GET("/app/trip/latest/{userIdx}")
    fun getRecentTrip(@Path("userIdx") userIdx: Int): Call<GetRecentTripResponse>
}