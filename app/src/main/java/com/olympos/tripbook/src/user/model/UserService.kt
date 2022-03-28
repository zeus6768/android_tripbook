package com.olympos.tripbook.src.user.model

import android.util.Log
import com.olympos.tripbook.utils.*
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofitWithoutAccessToken
import retrofit2.*

class UserService {
    private lateinit var userView: UserView

    fun setUserView(userView: UserView) {
        this.userView = userView
    }

    fun autoSignin() {
        val autoSiginRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        autoSiginRetrofit
            .autoSignin()
            .enqueue(object : Callback<SigninResponse> {
                override fun onResponse(call: Call<SigninResponse>, response: Response<SigninResponse>) {
                    Log.d("UserService.kt", "autoSignin()")
                    val body = response.body()!!
                    when (body.code) {
                        1001 -> {
                            saveUserIdx(body.result!!.userIdx)
                            userView.autoSigninSuccess()
                        }
                        else -> userView.autoSigninFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SigninResponse>, t: Throwable) {
                    Log.e("UserService.kt", "autoSignin() $t")
                    userView.autoSigninFailure(400)
                }
            })
    }

    fun signUpUser(kakaoAccessToken: String) {
        val signUpUserRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        signUpUserRetrofit
            .signUpUser(kakaoAccessToken)
            .enqueue(object : Callback<SignupResponse> {
                override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                    Log.d("UserService.kt", "signUpUser()")
                    val body = response.body()!!
                    when (body.code) {
                        1004 -> userView.signUpUserSuccess()
                        else -> userView.signUpUserFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("UserService.kt", "signUpUser() $t")
                    userView.signUpUserFailure(400)
                }
            })
    }

    fun signUpProfile(kakaoAccessToken: String) {
        val signUpProfileRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        signUpProfileRetrofit
            .signUpProfile(kakaoAccessToken)
            .enqueue(object : Callback<SignupResponse> {
                override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                    Log.d("UserService.kt", "signUpProfile()")
                    val body = response.body()!!
                    when (body.code) {
                        1005 -> userView.signUpProfileSuccess()
                        else -> userView.signUpProfileFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("UserService.kt", "signUpProfile() $t")
                    userView.signUpProfileFailure(400)
                }
            })
    }

    fun kakaoSignin(kakaoTokens: HashMap<String, String>) {
        val kakaoSigninRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        kakaoSigninRetrofit
            .kakaoSignin(kakaoTokens)
            .enqueue(object : Callback<KakaoSigninResponse> {
                override fun onResponse(call: Call<KakaoSigninResponse>, response: Response<KakaoSigninResponse>) {
                    Log.d("UserService.kt", "kakaoSignin()")
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> {
                            saveUserIdx(body.result!!.userIdx)
                            saveAccessToken(body.result!!.accessToken)
                            saveRefreshToken(body.result!!.refreshToken)
                            userView.kakaoSigninSuccess()
                        }
                        else -> userView.kakaoSigninFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<KakaoSigninResponse>, t: Throwable) {
                    Log.e("UserService.kt", "kakaoSignin() $t")
                    userView.kakaoSigninFailure(400)
                }
            })
    }

    fun updateKakaoAccessToken(kakaoRefreshToken: String, userIdx: Int) {
        val updateKakaoAccessTokenRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        updateKakaoAccessTokenRetrofit
            .updateKakaoAccessToken(kakaoRefreshToken, userIdx)
            .enqueue(object : Callback<UpdateKakaoAccessTokenResponse> {
                override fun onResponse(call: Call<UpdateKakaoAccessTokenResponse>, response: Response<UpdateKakaoAccessTokenResponse>) {
                    Log.d("UserService.kt", "updateKakaoAccessToken()")
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> {
                            saveKakaoAccessToken(body.result!!.accessToken)
                            userView.updateKakaoAccessTokenSuccess()
                        }
                        else -> userView.updateKakaoAccessTokenFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<UpdateKakaoAccessTokenResponse>, t: Throwable) {
                    Log.e("UserService.kt", "updateKakaoAccessToken() $t")
                    userView.updateAccessTokenFailure(400)
                }
            })
    }

    fun updateProfile(kakaoAccessToken: String, userIdx: Int) {
        val updateProfileRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        updateProfileRetrofit
            .updateProfile(kakaoAccessToken, userIdx)
            .enqueue(object : Callback<UpdateProfileResponse> {
                override fun onResponse(call: Call<UpdateProfileResponse>, response: Response<UpdateProfileResponse>) {
                    Log.e("UserService.kt", "updateProfile()")
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> userView.updateProfileSuccess()
                        else -> userView.updateProfileFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                    Log.e("UserService.kt", "updateProfile() $t")
                    userView.updateProfileFailure(400)
                }
            })
    }

    fun updateAccessToken(refreshToken: HashMap<String, String>, userIdx: Int) {
        val updateAccessTokenRetrofit = retrofitWithoutAccessToken.create(UserRetrofitInterface::class.java)
        updateAccessTokenRetrofit
            .updateAccessToken(refreshToken, userIdx)
            .enqueue(object : Callback<UpdateAccessTokenResponse> {
                override fun onResponse(call: Call<UpdateAccessTokenResponse>, response: Response<UpdateAccessTokenResponse>) {
                    Log.d("UserService.kt", "updateAccessToken()")
                    Log.e("UserService.kt", response.body().toString())
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> {
                            saveUserIdx(body.result!!.userIdx)
                            saveAccessToken(body.result!!.accessToken)
                            saveRefreshToken(body.result!!.refreshToken)
                            userView.updateAccessTokenSuccess()
                        }
                        else -> userView.updateAccessTokenFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<UpdateAccessTokenResponse>, t: Throwable) {
                    Log.e("UserService.kt", "updateAccessToken() $t")
                    userView.updateAccessTokenFailure(400)
                }
            })
    }
}