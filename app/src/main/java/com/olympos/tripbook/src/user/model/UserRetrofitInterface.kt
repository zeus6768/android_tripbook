package com.olympos.tripbook.src.user.model

import com.olympos.tripbook.utils.ApplicationClass.Companion.X_ACCESS_TOKEN
import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitInterface {
    @GET("/app/autosignin")
    fun autoSignin(): Call<SigninResponse>

    @POST("/app/kakao/signup")
    fun signUpUser(@Body kakaoAccessToken: String): Call<SignupResponse>

    @POST("/app/kakao/signupprofile")
    fun signUpProfile(@Body kakaoAccessToken: String): Call<SignupResponse>

    @POST("/app/kakao/signin")
    fun kakaoSignin(@Body params: HashMap<String, String>): Call<KakaoSigninResponse>

    @POST("/app/user/kakao/updateTokens/{userIdx}")
    fun updateKakaoAccessToken(
        @Body kakaoRefreshToken: String,
        @Path("userIdx") userIdx: Int
    ): Call<UpdateKakaoAccessTokenResponse>

    @POST("/app/user/kakao/updateUserProfile/{userIdx}")
    fun updateProfile(
        @Body kakaoAccessToken: String,
        @Path("userIdx") userIdx: Int
    ): Call<UpdateProfileResponse>

    @POST("/app/user/updateTokens/{userIdx}")
    fun updateAccessToken(
        @Body refreshToken: String,
        @Path("userIdx") userIdx: Int
    ): Call<UpdateAccessTokenResponse>
}