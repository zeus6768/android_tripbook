package com.olympos.tripbook.src.tripcourse_view.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViewRetrofitInterface {

    @GET("/app/trip/{userIdx}/{tripIdx}/courses")
    fun getTrip(@Path("userIdx") userIdx : String, @Path("tripIdx") tripIdx: String) : Call<GetTripResponse>

    @GET("/app/trip/latest/{userIdx}")
    fun getRecentTrip(@Path("userIdx") userIdx : String) : Call<GetTripResponse>

    @GET("/app/trip/latest/{userIdx}/courses")
    fun getRecentTripCards(@Path("userIdx") userIdx: String : String) : Call<GetTripResponse>
}