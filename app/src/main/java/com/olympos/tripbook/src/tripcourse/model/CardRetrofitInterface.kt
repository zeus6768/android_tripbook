package com.olympos.tripbook.src.tripcourse.model

import retrofit2.Call
import retrofit2.http.*

interface CardRetrofitInterface {
    @POST("/app/course")
    fun postCard(@Body card : Card): Call<CardResponse>

    @GET("/app/course/:courseIdx")
    fun getCard() : Call<CardResponse>
}