package com.olympos.tripbook.src.user.controller

import com.olympos.tripbook.src.user.model.*
import retrofit2.Call
import retrofit2.http.*

interface UserAuthApi {
    @GET("/app/autosignin")
    fun autoSignin(): Call<SigninResponse>

    @POST("/app/kakao/signup")
    fun signUpUser(@Body kakaoAccessToken: HashMap<String, String>): Call<SignupResponse>

    @POST("/app/kakao/signupprofile")
    fun signUpProfile(@Body kakaoAccessToken: HashMap<String, String>): Call<SignupResponse>

    @POST("/app/kakao/signin")
    fun kakaoSignin(@Body params: HashMap<String, String>): Call<KakaoSigninResponse>

    @POST("/app/user/kakao/updateTokens/{userIdx}")
    fun updateKakaoAccessToken(
        @Body kakaoRefreshToken: HashMap<String, String>,
        @Path("userIdx") userIdx: Int
    ): Call<UpdateKakaoAccessTokenResponse>

    @POST("/app/user/kakao/updateUserProfile/{userIdx}")
    fun updateProfile(
        @Body kakaoAccessToken: HashMap<String, String>,
        @Path("userIdx") userIdx: Int
    ): Call<UpdateProfileResponse>

    @GET("/app/user/{userIdx}")
    fun getProfile(@Path("userIdx") userIdx: Int): Call<GetProfileResponse>

    @POST("/app/user/updateTokens/{userIdx}")
    fun updateAccessToken(
        @Body refreshToken: HashMap<String, String>,
        @Path("userIdx") userIdx: Int
    ): Call<UpdateAccessTokenResponse>
}