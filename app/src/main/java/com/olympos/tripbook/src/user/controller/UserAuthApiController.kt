package com.olympos.tripbook.src.user.controller

import android.util.Log
import com.olympos.tripbook.src.user.view.UserAuthView
import com.olympos.tripbook.src.user.model.*
import com.olympos.tripbook.utils.*
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofitWithoutAccessToken
import retrofit2.*

class UserAuthApiController {

    private lateinit var userAuthApiView: UserAuthView

    fun setUserView(userAuthApiView: UserAuthView) {
        this.userAuthApiView = userAuthApiView
    }

    fun autoSignin() {
        val autoSiginRetrofit = retrofit.create(UserAuthApi::class.java)
        autoSiginRetrofit
            .autoSignin()
            .enqueue(object : Callback<SigninResponse> {
                override fun onResponse(
                    call: Call<SigninResponse>,
                    response: Response<SigninResponse>
                ) {
                    Log.d("UserAuthApiController", "autoSignin()")
                    val body = response.body()!!
                    when (body.code) {
                        1001 -> {
                            saveUserIdx(body.result!!.userIdx)
                            userAuthApiView.autoSignInSuccess()
                        }
                        else -> userAuthApiView.autoSignInFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SigninResponse>, t: Throwable) {
                    Log.e("UserAuthApiController", "autoSignin() $t")
                    userAuthApiView.autoSignInFailure(400)
                }
            })
    }

    fun signUpUser(kakaoAccessToken: HashMap<String, String>) {
        val signUpUserRetrofit = retrofit.create(UserAuthApi::class.java)
        signUpUserRetrofit
            .signUpUser(kakaoAccessToken)
            .enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    Log.d("UserAuthApiController", "signUpUser()")
                    val body = response.body()!!
                    when (body.code) {
                        1004 -> userAuthApiView.signUpUserSuccess()
                        else -> userAuthApiView.signUpUserFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("UserAuthApiController", "signUpUser() $t")
                    userAuthApiView.signUpUserFailure(400)
                }
            })
    }

    fun signUpProfile(kakaoAccessToken: HashMap<String, String>) {
        val signUpProfileRetrofit = retrofit.create(UserAuthApi::class.java)
        signUpProfileRetrofit
            .signUpProfile(kakaoAccessToken)
            .enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    Log.d("UserAuthApiController", "signUpProfile()")
                    val body = response.body()!!
                    when (body.code) {
                        1005 -> userAuthApiView.signUpProfileSuccess()
                        else -> userAuthApiView.signUpProfileFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("UserAuthApiController", "signUpProfile() $t")
                    userAuthApiView.signUpProfileFailure(400)
                }
            })
    }

    fun kakaoSignin(kakaoTokens: HashMap<String, String>) {
        val kakaoSigninRetrofit = retrofit.create(UserAuthApi::class.java)
        kakaoSigninRetrofit
            .kakaoSignin(kakaoTokens)
            .enqueue(object : Callback<KakaoSigninResponse> {
                override fun onResponse(
                    call: Call<KakaoSigninResponse>,
                    response: Response<KakaoSigninResponse>
                ) {
                    Log.d("UserAuthApiController", "kakaoSignin()")
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> {
                            saveUserIdx(body.result!!.userIdx)
                            saveAccessToken(body.result.accessToken)
                            saveRefreshToken(body.result.refreshToken)
                            userAuthApiView.kakaoSignInSuccess()
                        }
                        else -> userAuthApiView.kakaoSignInFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<KakaoSigninResponse>, t: Throwable) {
                    Log.e("UserAuthApiController", "kakaoSignin() $t")
                    userAuthApiView.kakaoSignInFailure(400)
                }
            })
    }

    fun updateKakaoAccessToken(kakaoRefreshToken: HashMap<String, String>, userIdx: Int) {
        val updateKakaoAccessTokenRetrofit = retrofit.create(UserAuthApi::class.java)
        updateKakaoAccessTokenRetrofit
            .updateKakaoAccessToken(kakaoRefreshToken, userIdx)
            .enqueue(object : Callback<UpdateKakaoAccessTokenResponse> {
                override fun onResponse(
                    call: Call<UpdateKakaoAccessTokenResponse>,
                    response: Response<UpdateKakaoAccessTokenResponse>
                ) {
                    Log.d("UserAuthApiController", "updateKakaoAccessToken()")
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> {
                            saveKakaoAccessToken(body.result!!.accessToken)
                            userAuthApiView.updateKakaoAccessTokenSuccess()
                        }
                        else -> userAuthApiView.updateKakaoAccessTokenFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<UpdateKakaoAccessTokenResponse>, t: Throwable) {
                    Log.e("UserAuthApiController", "updateKakaoAccessToken() $t")
                    userAuthApiView.updateAccessTokenFailure(400)
                }
            })
    }

    fun updateProfile(kakaoAccessToken: HashMap<String, String>, userIdx: Int) {
        val updateProfileRetrofit = retrofit.create(UserAuthApi::class.java)
        updateProfileRetrofit
            .updateProfile(kakaoAccessToken, userIdx)
            .enqueue(object : Callback<UpdateProfileResponse> {
                override fun onResponse(
                    call: Call<UpdateProfileResponse>,
                    response: Response<UpdateProfileResponse>
                ) {
                    Log.d("UserAuthApiController", "updateProfile()")
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> userAuthApiView.updateProfileSuccess()
                        else -> userAuthApiView.updateProfileFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                    Log.e("UserAuthApiController", "updateProfile() $t")
                    userAuthApiView.updateProfileFailure(400)
                }
            })
    }

    fun getProfile(userIdx: Int) {
        val getProfileRetrofit = retrofit.create(UserAuthApi::class.java)
        getProfileRetrofit
            .getProfile(userIdx)
            .enqueue(object : Callback<GetProfileResponse> {
                override fun onResponse(
                    call: Call<GetProfileResponse>,
                    response: Response<GetProfileResponse>
                ) {
                    Log.d("UserAuthApiController", "updateProfile()")
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> {
                            saveNickname(body.result!!.nickName)
                            saveUserImage(body.result.userImg)
                            userAuthApiView.getProfileSuccess()
                        }
                        else -> userAuthApiView.getProfileFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                    Log.e("UserAuthApiController", "getProfile() $t")
                    userAuthApiView.getProfileFailure(400)
                }
            })
    }

    fun updateAccessToken(refreshToken: HashMap<String, String>, userIdx: Int) {
        val updateAccessTokenRetrofit = retrofitWithoutAccessToken.create(UserAuthApi::class.java)
        updateAccessTokenRetrofit
            .updateAccessToken(refreshToken, userIdx)
            .enqueue(object : Callback<UpdateAccessTokenResponse> {
                override fun onResponse(
                    call: Call<UpdateAccessTokenResponse>,
                    response: Response<UpdateAccessTokenResponse>
                ) {
                    Log.d("UserAuthApiController", "updateAccessToken()")
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> {
                            saveUserIdx(body.result!!.userIdx)
                            saveAccessToken(body.result.accessToken)
                            saveRefreshToken(body.result.refreshToken)
                            userAuthApiView.updateAccessTokenSuccess()
                        }
                        else -> userAuthApiView.updateAccessTokenFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<UpdateAccessTokenResponse>, t: Throwable) {
                    Log.e("UserAuthApiController", "updateAccessToken() $t")
                    userAuthApiView.updateAccessTokenFailure(400)
                }
            })
    }
}