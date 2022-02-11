package com.olympos.tripbook.src.tripcourse.model

import retrofit2.Call
import retrofit2.http.*

interface CardRetrofitInterface {
    @POST("/app/course")
    fun postCard(@Body card : Card): Call<PostCardResponse>
    //Gson 객체로 바꿔서 서버로 보내는 어노테이션 : @Body

    @GET("/app/course/:courseIdx")
    fun getCard() : Call<GetCardResponse>

    @GET("/app/trip/:tripIdx/courses")
    fun getTrip(@Path("tripIdx") tripIdx : Int) : Call<GetTripResponse>
}