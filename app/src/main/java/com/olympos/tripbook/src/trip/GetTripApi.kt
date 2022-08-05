package com.olympos.tripbook.src.trip

import com.olympos.tripbook.src.trip.model.GetTripResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetTripApi {
    @GET("/app/trip/latest/{userIdx}")
    fun getTrip(@Path("userIdx") userIdx: Int): Call<GetTripResponse>
}