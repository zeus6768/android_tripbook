package com.olympos.tripbook.src.trip.model

import android.util.Log
import com.olympos.tripbook.src.trip.TripActivity
import com.olympos.tripbook.src.user.model.ApiTestResponse
import com.olympos.tripbook.src.user.model.ApiTestRetrofitInterface
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.ApplicationClass.Companion.TAG
import com.olympos.tripbook.utils.saveNickname
import retrofit2.*

class TripService {
    private lateinit var process: TripPostProcess

    fun setloadingView(process: TripPostProcess) {
        this.process = process
    }
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
        Log.d("들어오는지 확인", "TripService-postTrip")
        val tripRetrofitService = retrofit.create(TripRetrofitInterface::class.java)

        //호출 전 loading
        process.onPostTripLoading()

        //api 호출 후 응답받으면 callback
        tripRetrofitService.postTrip(trip).enqueue(object : Callback<TripResponse> {
            override fun onResponse(call: Call<TripResponse>, response: Response<TripResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("__res", response.body()!!.toString())
                    when (res.code) {
                        1000 -> process.onPostTripSuccess(res.result)
                        else -> process.onPostTripFailure(res.code, res.message)
                    }
                }
            }

            //네트워크 실패
            override fun onFailure(call: Call<TripResponse>, t: Throwable) {
                process.onPostTripFailure(400, t.message.toString())
            }
        })
    }
}
