package com.olympos.tripbook.src.trip.model

import retrofit2.Call
import retrofit2.http.*

interface TripRetrofitInterface {
    @POST("/app/trip")
    fun postTrip(@Body trip: Trip): Call<TripResponse>

    @GET("/app/trip/latest/{userIdx}")
    fun getTrip(@Path("userIdx") userIdx: Int): Call<TripResponse2>
}