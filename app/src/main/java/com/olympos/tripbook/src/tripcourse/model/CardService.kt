package com.olympos.tripbook.src.tripcourse.model

import android.util.Log
import com.olympos.tripbook.src.user.model.ApiTestResponse
import com.olympos.tripbook.src.user.model.ApiTestRetrofitInterface
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.ApplicationClass.Companion.TAG
import com.olympos.tripbook.utils.saveNickname
import retrofit2.*

class CardService {
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

    fun postCard(card : Card) {
        Log.d("들어오는지 확인", "CardService-postCard")
        val cardRetrofitService = retrofit.create(CardRetrofitInterface::class.java)
        cardRetrofitService.postCard(card).enqueue(object : Callback<CardResponse> {
            override fun onResponse(call: Call<CardResponse>, response: Response<CardResponse>) {
                Log.d("들어오는지 확인", "CardService-postCard-onResponse")
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("__res", response.body()!!.toString())
                    when (res.code) {
                        1000 -> Log.d("REST API TEST 성공", res.toString())
                        2201 -> Log.d("통신 실패 : ", res.toString())
                        2202 -> Log.d("통신 실패 : ", res.toString())
                        2203 -> Log.d("통신 실패 : ", res.toString())
                        2204 -> Log.d("통신 실패 : ", res.toString())
                        2205 -> Log.d("통신 실패 : ", res.toString())
                        2206 -> Log.d("통신 실패 : ", res.toString())
                        2211 -> Log.d("통신 실패 : ", res.toString())
                    }
                }
            }
            override fun onFailure(call: Call<CardResponse>, t: Throwable) {
                Log.d("들어오는지 확인", "CardService-postCard-onFailure")
            }
        })
    }

    fun getCard(){

        Log.d("들어오는지 확인", "CardService-postCard")

        val cardRetrofitService = retrofit.create(CardRetrofitInterface::class.java)

        cardRetrofitService.getCard().enqueue(object : Callback<CardResponse> {
            override fun onResponse(call: Call<CardResponse>, response: Response<CardResponse>) {
                Log.d("들어오는지 확인", "CardService-postCard-onResponse")
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("__res", response.body()!!.toString())
                    when (res.code) {
                        1000 -> Log.d("REST API TEST 성공", res.toString())
                        2201 -> Log.d("통신 실패 : ", res.toString())
                        2202 -> Log.d("통신 실패 : ", res.toString())
                        2203 -> Log.d("통신 실패 : ", res.toString())
                        2204 -> Log.d("통신 실패 : ", res.toString())
                        2205 -> Log.d("통신 실패 : ", res.toString())
                        2206 -> Log.d("통신 실패 : ", res.toString())
                        2211 -> Log.d("통신 실패 : ", res.toString())
                    }
                }
            }
            override fun onFailure(call: Call<CardResponse>, t: Throwable) {
                Log.d("들어오는지 확인", "CardService-postCard-onFailure")
            }
        })
    }
}
