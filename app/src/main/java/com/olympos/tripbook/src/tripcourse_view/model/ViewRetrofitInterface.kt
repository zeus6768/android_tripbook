package com.olympos.tripbook.src.tripcourse_view.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViewRetrofitInterface {

    @GET("/app/trip/{tripIdx}/courses")
    fun getTrip(@Path("tripIdx") tripIdx : String) : Call<GetTripResponse>

}