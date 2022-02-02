package com.olympos.tripbook.src.user.model

import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitInterface {
    @POST("/app/user")
    fun postTrip(): Call<UserResponse>
}