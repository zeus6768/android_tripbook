package com.olympos.tripbook.src.user.model

import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitInterface {
    @GET("/app/test")
    fun getUser(): Call<UserResponse>

    @POST("/app/signin")
    fun postUser(): Call<UserResponse>
}