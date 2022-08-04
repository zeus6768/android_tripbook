package com.olympos.tripbook.src.trip

import com.olympos.tripbook.src.trip.model.GetTripResponse
import com.olympos.tripbook.src.trip.model.PostTripResponse
import com.olympos.tripbook.src.trip.model.Trip
import retrofit2.Call
import retrofit2.http.*

interface TripApi {
    @POST("/app/trip")
    fun postTrip(@Body trip: Trip): Call<PostTripResponse>

    @GET("/app/trip/latest/{userIdx}")
    fun getTrip(@Path("userIdx") userIdx: Int): Call<GetTripResponse>
}