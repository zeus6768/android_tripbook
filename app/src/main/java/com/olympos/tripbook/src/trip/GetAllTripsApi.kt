package com.olympos.tripbook.src.trip

import com.olympos.tripbook.src.trip.model.GetAllTripsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetAllTripsApi {
    @GET("/app/trips/{userIdx}")
    fun getAllTrips(@Path("userIdx") userIdx: Int): Call<GetAllTripsResponse>
}