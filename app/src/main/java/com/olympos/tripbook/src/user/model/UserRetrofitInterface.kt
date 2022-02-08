package com.olympos.tripbook.src.user.model

import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitInterface {
    @POST("/app/test")
    fun postUser(): Call<UserResponse>

    @POST("/app/test")
    fun postRefreshJWT(@Body reFreshJwt: String): Call<RefreshJwtResponse>

    @POST("/app/kakao/login")
    fun postKakaoAccessToken(@Body kakaoAccessToken: String): Call<KakaoAccessTokenResponse>

    @POST("/app/kakao/login/refresh")
    fun postKakaoRefreshToken(@Body kakaoRefreshToken: String): Call<KakaoRefreshTokenResponse>
}