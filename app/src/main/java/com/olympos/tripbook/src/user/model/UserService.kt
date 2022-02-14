package com.olympos.tripbook.src.user.model

import android.util.Log
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.ApplicationClass.Companion.TAG
import com.olympos.tripbook.utils.saveAccessToken
import com.olympos.tripbook.utils.saveRefreshToken
import com.olympos.tripbook.utils.saveUserIdx
import retrofit2.*

class UserService {
    fun autoSignin(accessToken: String): Boolean {
        var result = false
        val postUserRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        postUserRetrofit
            .autoSignin(accessToken)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    val body = response.body()!!
                    when (body.code) {
                        1001 -> {
                            saveUserIdx(body.result!!.userIdx)
                            Log.d("autoSignin API", "Success")
                            result = true
                        }
                    }
                }
                override fun onFailure(
                    call: Call<UserResponse>,
                    t: Throwable
                ) {
                    Log.e("autoSignin API", t.toString())
                }
            })
        return result
    }

    fun postRefreshToken(refreshToken: String) {
        val postRefreshTokenRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        postRefreshTokenRetrofit
            .postRefreshToken(refreshToken)
            .enqueue(object : Callback<RefreshTokenResponse> {
                override fun onResponse(
                    call: Call<RefreshTokenResponse>,
                    response: Response<RefreshTokenResponse>
                ) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(
                    call: Call<RefreshTokenResponse>,
                    t: Throwable
                ) {
                    Log.e("postRefreshToken API", t.toString())
                }
            })
    }

    fun postKakaoTokens(kakaoTokens: HashMap<String, String>) {
        val postKakaoTokenRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        postKakaoTokenRetrofit
            .postKakaoTokens(kakaoTokens)
            .enqueue(object : Callback<KakaoTokensResponse> {
                override fun onResponse(
                    call: Call<KakaoTokensResponse>,
                    response: Response<KakaoTokensResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()!!
                        if (body.isSuccess) {
                            val result = response.body()?.result
                            saveUserIdx(result!!.userIdx)
                            saveAccessToken(result!!.jwt)
                            saveRefreshToken(result!!.jwtRefreshToken)
                        }
                        Log.d("POST API Success", body.toString())
                    }
                }
                override fun onFailure(
                    call: Call<KakaoTokensResponse>,
                    t: Throwable
                ) {
                    Log.d("POST API Failure", t.toString())
                }
            })
    }

    private fun signUp(kakaoAccessToken: String) {
        val signUpRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        signUpRetrofit.signUp(kakaoAccessToken)
            .enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("SignUp API", "Success")
                        signUpProfile(kakaoAccessToken)
                    }
                }
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.d("SignUp API", "Faliure")
                }
            })
    }

    private fun signUpProfile(kakaoAccessToken: String) {
        val signUpProfileRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        signUpProfileRetrofit
            .signUpProfile(kakaoAccessToken)
            .enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    Log.d("SignUpProfile API", "Success")
                }
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.d("SignUpProfile API", "Faliure")
                }
            })
    }
}
