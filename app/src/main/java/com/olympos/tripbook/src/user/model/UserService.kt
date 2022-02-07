package com.olympos.tripbook.src.user.model

import android.util.Log
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.ApplicationClass.Companion.TAG
import com.olympos.tripbook.utils.saveJwt
import retrofit2.*

class UserService {
    fun getApiTest() {
        val apiTestService = retrofit.create(ApiTestRetrofitInterface::class.java)
        apiTestService.getApiTest().enqueue(object : Callback<ApiTestResponse> {
            override fun onResponse(call: Call<ApiTestResponse>, response: Response<ApiTestResponse>) {
                Log.d(TAG, response.toString())
                if (response.isSuccessful) {
                    val res = response.body()!!
                    when (res.code) {
                        1000 -> Log.d("REST API TEST 성공", res.toString())
                    }
                }
            }
            override fun onFailure(call: Call<ApiTestResponse>, t: Throwable) {
                Log.d("REST API 실패", t.message.toString())
            }
        })
    }

    fun postKakaoAccessToken(kakaoAccessToken: String) {
        val postJwtRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        postJwtRetrofit.postUser(kakaoAccessToken).enqueue(object : Callback<KakaoAccessTokenResponse> {
            override fun onResponse(call: Call<KakaoAccessTokenResponse>, response: Response<KakaoAccessTokenResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    when (res.code) {
                        1000 -> saveJwt(res.)
                    }
                }
            }
            override fun onFailure(call: Call<JwtResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun postRefreshJwt(refreshJwtRequest: RefreshJwtRequest) {

    }

    fun postKakaoAccessToken(kakaoAccessTokenRequest: KakaoAccessTokenRequest) {

    }

    fun postKakaoRefreshToken(kakaoRefreshTokenRequest: KakaoRefreshTokenRequest) {

    }

}
