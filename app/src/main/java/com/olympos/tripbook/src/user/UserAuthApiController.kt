package com.olympos.tripbook.src.user

import android.util.Log
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
                    Log.d("UserService.kt", "autoSignin()")
                    val body = response.body()!!
                    when (body.code) {
                        1001 -> {
                            saveUserIdx(body.result!!.userIdx)
                            userAuthApiView.autoSigninSuccess()
                        }
                        else -> userAuthApiView.autoSigninFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SigninResponse>, t: Throwable) {
                    Log.e("UserService.kt", "autoSignin() $t")
                    userAuthApiView.autoSigninFailure(400)
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
                    Log.d("UserService.kt", "signUpUser()")
                    val body = response.body()!!
                    when (body.code) {
                        1004 -> userAuthApiView.signUpUserSuccess()
                        else -> userAuthApiView.signUpUserFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("UserService.kt", "signUpUser() $t")
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
                    Log.d("UserService.kt", "signUpProfile()")
                    val body = response.body()!!
                    when (body.code) {
                        1005 -> userAuthApiView.signUpProfileSuccess()
                        else -> userAuthApiView.signUpProfileFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("UserService.kt", "signUpProfile() $t")
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
                    Log.d("UserService.kt", "kakaoSignin()")
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> {
                            saveUserIdx(body.result!!.userIdx)
                            saveAccessToken(body.result.accessToken)
                            saveRefreshToken(body.result.refreshToken)
                            userAuthApiView.kakaoSigninSuccess()
                        }
                        else -> userAuthApiView.kakaoSigninFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<KakaoSigninResponse>, t: Throwable) {
                    Log.e("UserService.kt", "kakaoSignin() $t")
                    userAuthApiView.kakaoSigninFailure(400)
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
                    Log.d("UserService.kt", "updateKakaoAccessToken()")
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
                    Log.e("UserService.kt", "updateKakaoAccessToken() $t")
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
                    Log.d("UserService.kt", "updateProfile()")
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> userAuthApiView.updateProfileSuccess()
                        else -> userAuthApiView.updateProfileFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                    Log.e("UserService.kt", "updateProfile() $t")
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
                    Log.d("UserService.kt", "updateProfile()")
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
                    Log.e("UserService.kt", "getProfile() $t")
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
                    Log.d("UserService.kt", "updateAccessToken()")
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
                    Log.e("UserService.kt", "updateAccessToken() $t")
                    userAuthApiView.updateAccessTokenFailure(400)
                }
            })
    }
}