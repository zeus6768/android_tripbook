package com.olympos.tripbook.src.user.model

import android.util.Log
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.ApplicationClass.Companion.TAG
import com.olympos.tripbook.utils.saveNickname
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

    fun postUser(userRequest: UserRequest) {
        val userRetrofitService = retrofit.create(UserRetrofitInterface::class.java)
        userRetrofitService.postUser(userRequest).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                // Log.d(TAG, response.toString())
                if (response.isSuccessful) {
                    val body = response.body()!!
                    if (body.isValidToken) acceptUser() else gotoSignin()
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("POST USER API Failure", t.message.toString())
                gotoSignin()
            }
        })
    }

    private fun acceptUser() {
        TODO("Not yet implemented")
    }

    private fun gotoSignin() {
        TODO("Not yet implemented")
    }
}
