package com.olympos.tripbook.src.user.model

import android.util.Log
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.ApplicationClass.Companion.TAG
import com.olympos.tripbook.utils.saveAccessToken
import com.olympos.tripbook.utils.saveRefreshToken
import com.olympos.tripbook.utils.saveUserIdx
import retrofit2.*

class UserService {
    fun autoSignin(accessToken: String) {
        val autoSiginRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        autoSiginRetrofit
            .autoSignin(accessToken)
            .enqueue(object : Callback<SigninResponse> {
                override fun onResponse(
                    call: Call<SigninResponse>,
                    response: Response<SigninResponse>
                ) {
                    val body = response.body()!!
                    when (body.code) {
                        1001 -> {
                            saveUserIdx(body.result!!.userIdx)
                            Log.d("autoSignin API", "UserIdx ${body.result!!.userIdx}")
                        }
                    }
                }
                override fun onFailure(
                    call: Call<SigninResponse>,
                    t: Throwable
                ) {
                    Log.e("autoSignin API", t.toString())
                }
            })
    }

    fun signUpUser(kakaoAccessToken: String) {
        val signUpUserRetrofit = retrofit.create(UserRetrofitInterface::class.java)
        signUpUserRetrofit
            .signUpUser(kakaoAccessToken)
            .enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    TODO("Not yet implemented")
                }
                override fun onFailure(
                    call: Call<SignupResponse>,
                    t: Throwable
                ) {
                    Log.e("signUpUser API", t.toString())
                }
            })
    }
}
