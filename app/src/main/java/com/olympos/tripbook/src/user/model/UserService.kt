package com.olympos.tripbook.src.user.model

import android.util.Log
import com.olympos.tripbook.utils.*
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofitWithoutAccessToken
import retrofit2.*

class UserService {
    private lateinit var splashView: SplashView
    private lateinit var signinView: SigninView

    fun setUserView(splashView: SplashView) {
        this.splashView = splashView
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
                            splashView.autoSigninSuccess()
                        }
                        else -> splashView.autoSigninFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SigninResponse>, t: Throwable) {
                    Log.e("UserService.kt", "autoSignin() $t")
                    splashView.autoSigninFailure(400)
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
                        1004 -> signinView.signUpUserSuccess()
                        else -> signinView.signUpUserFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("UserService.kt", "signUpUser() $t")
                    signinView.signUpUserFailure(400)
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
                        1005 -> signinView.signUpProfileSuccess()
                        else -> signinView.signUpProfileFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("UserService.kt", "signUpProfile() $t")
                    signinView.signUpProfileFailure(400)
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
                            signinView.kakaoSigninSuccess()
                        }
                        else -> signinView.kakaoSigninFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<KakaoSigninResponse>, t: Throwable) {
                    Log.e("UserService.kt", "kakaoSignin() $t")
                    signinView.kakaoSigninFailure(400)
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
                            signinView.updateKakaoAccessTokenSuccess()
                        }
                        else -> signinView.updateKakaoAccessTokenFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<UpdateKakaoAccessTokenResponse>, t: Throwable) {
                    Log.e("UserService.kt", "updateKakaoAccessToken() $t")
                    splashView.updateAccessTokenFailure(400)
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
                        1000 -> signinView.updateProfileSuccess()
                        else -> signinView.updateProfileFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                    Log.e("UserService.kt", "updateProfile() $t")
                    signinView.updateProfileFailure(400)
                }
            })
    }

    fun updateAccessToken(refreshToken: String, userIdx: Int) {
        val updateAccessTokenRetrofit = retrofitWithoutAccessToken.create(UserRetrofitInterface::class.java)
        updateAccessTokenRetrofit
            .updateAccessToken(refreshToken, userIdx)
            .enqueue(object : Callback<UpdateAccessTokenResponse> {
                override fun onResponse(call: Call<UpdateAccessTokenResponse>, response: Response<UpdateAccessTokenResponse>) {
                    Log.d("UserService.kt", "updateAccessToken()")
                    Log.e("UserService.kt", response.toString())
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> {
                            saveUserIdx(body.result!!.userIdx)
                            saveAccessToken(body.result!!.accessToken)
                            saveRefreshToken(body.result!!.refreshToken)
                            splashView.updateAccessTokenSuccess()
                        }
                        else -> splashView.updateAccessTokenFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<UpdateAccessTokenResponse>, t: Throwable) {
                    Log.e("UserService.kt", "updateAccessToken() $t")
                    splashView.updateAccessTokenFailure(400)
                }
            })
    }
}