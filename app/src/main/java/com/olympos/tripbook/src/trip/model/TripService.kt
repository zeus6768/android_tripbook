package com.olympos.tripbook.src.trip.model

import android.util.Log
import com.olympos.tripbook.src.user.model.ApiTestResponse
import com.olympos.tripbook.src.user.model.ApiTestRetrofitInterface
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.ApplicationClass.Companion.TAG
import com.olympos.tripbook.utils.saveNickname
import retrofit2.*

class TripService {
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

    fun postTrip(trip: Trip) {
        val tripRetrofitService = retrofit.create(TripRetrofitInterface::class.java)
        tripRetrofitService.postTrip(trip).enqueue(object : Callback<TripResponse> {
            override fun onResponse(call: Call<TripResponse>, response: Response<TripResponse>) {
                // Log.d(TAG, response.toString())
                if (response.isSuccessful) {
                    val res = response.body()!!
                    when (res.code) {
                        1000 -> Log.d("REST API TEST 성공", res.toString())
                    }
                }
            }
            override fun onFailure(call: Call<TripResponse>, t: Throwable) {
                Log.d("POST TRIP API Failure", t.message.toString())
            }
        })
    }
}
