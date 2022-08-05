package com.olympos.tripbook.src.trip

import com.olympos.tripbook.src.trip.model.PostTripResponse
import com.olympos.tripbook.src.trip.model.Trip
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PostTripApi {
    @POST("/app/trip")
    fun postTrip(@Body trip: Trip): Call<PostTripResponse>
}