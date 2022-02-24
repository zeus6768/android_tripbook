package com.olympos.tripbook.src.user.model

import android.util.Log
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.saveAccessToken
import com.olympos.tripbook.utils.saveRefreshToken
import com.olympos.tripbook.utils.saveUserIdx
import retrofit2.*

class UserService {
    private lateinit var userView: UserView

    fun setUserView(userView: UserView) {
        this.userView = userView
    }

    fun autoSignin(accessToken: String) {
        val autoSiginRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        autoSiginRetrofit
            .autoSignin(accessToken)
            .enqueue(object : Callback<SigninResponse> {
                override fun onResponse(call: Call<SigninResponse>, response: Response<SigninResponse>) {
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
                    Log.e("autoSignin API", t.toString())
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
                    val body = response.body()!!
                    when (body.code) {
                        1004 -> userView.signUpUserSuccess()
                        else -> userView.signUpUserFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("signUpUser API", t.toString())
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
                    val body = response.body()!!
                    when (body.code) {
                        1005 -> userView.signUpProfileSuccess()
                        else -> userView.signUpProfileFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("signUpProfile API", t.toString())
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
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> {
                            saveAccessToken(body.result!!.accessToken)
                            saveRefreshToken(body.result!!.refreshToken)
                            userView.kakaoSigninSuccess()
                        }
                        else -> userView.kakaoSigninFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<KakaoSigninResponse>, t: Throwable) {
                    Log.e("kakaoSignin API", t.toString())
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
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> userView.updateKakaoAccessTokenSuccess()
                        else -> userView.updateKakaoAccessTokenFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<UpdateKakaoAccessTokenResponse>, t: Throwable) {
                    Log.e("updateKakaoAccess API", t.toString())
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
                    val body = response.body()!!
                    when (body.code) {
                        1000 -> userView.updateProfileSuccess()
                        else -> userView.updateProfileFailure(body.code)
                    }
                }
                override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                    Log.e("updateProfile API", t.toString())
                    userView.updateProfileFailure(400)
                }
            })
    }

    fun updateAccessToken(refreshToken: String) {
        val updateAccessTokenRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        updateAccessTokenRetrofit
            .updateAccessToken(refreshToken)
            .enqueue(object : Callback<UpdateAccessTokenResponse> {
                override fun onResponse(call: Call<UpdateAccessTokenResponse>, response: Response<UpdateAccessTokenResponse>) {
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
                    Log.e("updateAccessToken API", t.toString())
                    userView.updateAccessTokenFailure(400)
                }
            })
    }
}
