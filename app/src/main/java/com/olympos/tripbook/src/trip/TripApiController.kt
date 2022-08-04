package com.olympos.tripbook.src.trip

import android.util.Log
import com.olympos.tripbook.src.trip.model.*
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.getUserIdx
import retrofit2.*

class TripApiController {
    private lateinit var process: PostTripView
    private lateinit var getProcess: GetTripView

    fun setProcess(process: PostTripView) {
        this.process = process
    }

    fun setGetProcess(getProcess: GetTripView) {
        this.getProcess = getProcess
    }

    fun postTrip(trip: Trip) {
        Log.d("TripService.kt", "postTrip()")
        val tripRetrofitService = retrofit.create(TripApi::class.java)

        //호출 전 loading
        process.onPostTripLoading()

        //api 호출 후 응답받으면 callback
        tripRetrofitService.postTrip(trip).enqueue(object : Callback<PostTripResponse> {
            override fun onResponse(call: Call<PostTripResponse>, response: Response<PostTripResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("TripService.kt", "postTrip()" + response.body()!!.toString())
                    when (res.code) {
                        1000 -> process.onPostTripSuccess(res.result)
                        else -> process.onPostTripFailure(res.code, res.message)
                    }
                }
            }

            //네트워크 실패
            override fun onFailure(call: Call<PostTripResponse>, t: Throwable) {
                Log.e("TripService.kt", "postTrip() failure $t")
                process.onPostTripFailure(400, t.message.toString())
            }
        })
    }

    fun getTrip() {
        Log.d("TripService.kt", "getTrip()")
        val tripRetrofitService = retrofit.create(TripApi::class.java)

        //호출 전 loading
        getProcess.onGetTripLoading()

        //api 호출 후 응답받으면 callback
        tripRetrofitService.getTrip(getUserIdx()).enqueue(object : Callback<GetTripResponse> {
            override fun onResponse(call: Call<GetTripResponse>, response: Response<GetTripResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("TripService.kt", "getTrip()" + response.body()!!.toString())
                    when (res.code) {
                        1000 -> getProcess.onGetTripSuccess(res.result)
                        else -> getProcess.onGetTripFailure(res.code, res.message)
                    }
                }
            }

            //네트워크 실패
            override fun onFailure(call: Call<GetTripResponse>, t: Throwable) {
                Log.e("TripService.kt", "getTrip() failure $t")
                getProcess.onGetTripFailure(400, t.message.toString())
            }
        })
    }
}