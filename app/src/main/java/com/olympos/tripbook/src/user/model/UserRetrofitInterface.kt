package com.olympos.tripbook.src.user.model

import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitInterface {
    @POST("/app/signin")
    fun postUser(@Header("jwt") jwt: String): Call<JwtResponse>

    @POST("/app/kakao/login")
    fun postKakaoAccessToken(@Body kakaoAccessToken: String): Call<KakaoAccessTokenResponse>
}