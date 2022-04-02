package com.olympos.tripbook.src.tripcourse_view.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViewRetrofitInterface {

    @GET("/app/trip/{userIdx}/{tripIdx}/courses")
    fun getTrip(@Path("userIdx") userIdx : Int, @Path("tripIdx") tripIdx: Int) : Call<GetTripResponse>

    @GET("/app/trip/latest/{userIdx}")
    fun getRecentTrip(@Path("userIdx") userIdx : Int) : Call<GetRecentTripResponse>

    @GET("/app/trip/latest/{userIdx}/courses")
    fun getRecentTripCards(@Path("userIdx") userIdx: Int) : Call<GetRecentTripCardsResponse>
}