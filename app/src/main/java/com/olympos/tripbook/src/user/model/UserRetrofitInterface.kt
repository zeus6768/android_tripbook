package com.olympos.tripbook.src.user.model

import com.olympos.tripbook.utils.ApplicationClass.Companion.X_ACCESS_TOKEN
import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitInterface {
    @GET("/app/autosignin")
    fun autoSignin(@Header(X_ACCESS_TOKEN) accessToken: String?): Call<UserResponse>

    @POST("/app/kakao/signup")
    fun signUp(@Body kakaoAccessToken: String): Call<SignupResponse>

    @POST("/app/kakao/signupprofile")
    fun signUpProfile(@Body kakaoAccessToken: String): Call<SignupResponse>

    @POST("/app/test")
    fun postRefreshToken(@Body refreshToken: String): Call<RefreshTokenResponse>

    @POST("/app/kakao/login")
    fun postKakaoTokens(@Body params: HashMap<String, String>): Call<KakaoTokensResponse>
}