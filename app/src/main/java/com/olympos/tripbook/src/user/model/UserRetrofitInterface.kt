package com.olympos.tripbook.src.user.model

import com.olympos.tripbook.utils.ApplicationClass.Companion.X_ACCESS_TOKEN
import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitInterface {
    @GET("/app/autosignin")
    fun autoSignin(@Header(X_ACCESS_TOKEN) accessToken: String?): Call<SigninResponse>

    @POST("/app/kakao/signup")
    fun signUpUser(@Body kakaoAccessToken: String): Call<SignupResponse>

    @POST("/app/kakao/signupprofile")
    fun signUpProfile(@Body kakaoAccessToken: String): Call<SignupResponse>

    @POST("/app/kakao/signin")
    fun kakaoSignin(@Body params: HashMap<String, String>): Call<KakaoSigninResponse>

    @POST("/app/user/kakao/updateTokens/{userIdx}")
    fun updateKakaoAccessToken(
        @Body params: HashMap<String, String>,
        @Path("userIdx") userIdx: String
    ): Call<updateKakaoAccessTokenResponse>

    @POST("/app/user/kakao/updateUserProfile/{userIdx}")
    fun updateProfile(
        @Body kakaoAccessToken: String,
        @Path("userIdx") userIdx: String
    ): Call<UpdateProfileResponse>

    @POST("/app/user/updateTokens")
    fun updateAccessToken(@Body refreshToken: String): Call<UpdateAccessTokenResponse>
}