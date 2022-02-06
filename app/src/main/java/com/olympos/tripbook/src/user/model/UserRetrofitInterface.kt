package com.olympos.tripbook.src.user.model

import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitInterface {
    @POST("/app/signin")
    fun postUser(@Body userRequest: UserRequest): Call<UserResponse>
}