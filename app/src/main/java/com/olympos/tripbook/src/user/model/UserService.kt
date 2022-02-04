package com.olympos.tripbook.src.user.model

import android.util.Log
import com.olympos.tripbook.utils.SocketApplication.Companion.retrofit
import com.olympos.tripbook.utils.SocketApplication.Companion.TAG
import retrofit2.*

class UserService {
    fun getApiTest() {
        val userService = retrofit.create(ApiTestRetrofitInterface::class.java)
        userService.getApiTest().enqueue(object: Callback<ApiTestResponse> {
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

    fun getUser() {

    }

    fun postUser() {

    }
}
