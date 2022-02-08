package com.olympos.tripbook.src.user.model

import android.util.Log
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.ApplicationClass.Companion.TAG
import com.olympos.tripbook.utils.saveJwt
import com.olympos.tripbook.utils.saveUserIdx
import retrofit2.*

class UserService {
    fun getApiTest() {
        val apiTestService = retrofit.create(ApiTestRetrofitInterface::class.java)
        apiTestService
            .getApiTest()
            .enqueue(object : Callback<ApiTestResponse> {
            override fun onResponse(
                call: Call<ApiTestResponse>,
                response: Response<ApiTestResponse>
            ) {
                Log.d(TAG, response.toString())
                if (response.isSuccessful) {
                    val res = response.body()!!
                    when (res.code) {
                        1000 -> Log.d("REST API 성공", res.toString())
                    }
                }
            }
            override fun onFailure(
                call: Call<ApiTestResponse>,
                t: Throwable
            ) {
                Log.d("REST API 실패", t.message.toString())
            }
        })
    }

    fun postUser() {
        val postUserRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        postUserRetrofit
            .postUser()
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    TODO("Not yet implemented")
                }
                override fun onFailure(
                    call: Call<UserResponse>,
                    t: Throwable
                ) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun postRefreshJwt(refreshJwt: String) {
        val postRefreshJwtRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        postRefreshJwtRetrofit
            .postRefreshJWT(refreshJwt)
            .enqueue(object : Callback<RefreshJwtResponse> {
                override fun onResponse(
                    call: Call<RefreshJwtResponse>,
                    response: Response<RefreshJwtResponse>
                ) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(
                    call: Call<RefreshJwtResponse>,
                    t: Throwable
                ) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun postKakaoAccessToken(kakaoAccessToken: String) {
        val postKakaoAccessTokenRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        postKakaoAccessTokenRetrofit
            .postKakaoAccessToken(kakaoAccessToken)
            .enqueue(object : Callback<KakaoAccessTokenResponse> {
                override fun onResponse(
                    call: Call<KakaoAccessTokenResponse>,
                    response: Response<KakaoAccessTokenResponse>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()!!.result
                        saveUserIdx(result!!.userIdx)
                        saveJwt(result!!.jwt)
                        Log.d("POST API Success", result.toString())
                    }
                }
                override fun onFailure(
                    call: Call<KakaoAccessTokenResponse>,
                    t: Throwable
                ) {
                    Log.d("POST API Failure", t.toString())
                }
            })
    }

    fun postKakaoRefreshToken(kakaoRefreshToken: String) {
        val postKakaoRefreshTokenRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        postKakaoRefreshTokenRetrofit
            .postKakaoRefreshToken(kakaoRefreshToken)
    }

}
