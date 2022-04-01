package com.olympos.tripbook.src.trip.model

import android.util.Log
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.getUserIdx
import retrofit2.*

class TripService {
    private lateinit var process: TripPostProcess
    private lateinit var getProcess: TripGetProcess

    fun setProcess(process: TripPostProcess) {
        this.process = process
    }

    fun setGetProcess(getProcess: TripGetProcess) {
        this.getProcess = getProcess
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

    fun getTrip() {
        Log.d("들어오는지 확인", "TripService-getTrip")
        val tripRetrofitService = retrofit.create(TripRetrofitInterface::class.java)

        //호출 전 loading
        getProcess.onGetTripLoading()

        //api 호출 후 응답받으면 callback
        tripRetrofitService.getTrip(getUserIdx()).enqueue(object : Callback<TripResponse2> {
            override fun onResponse(call: Call<TripResponse2>, response: Response<TripResponse2>) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("__res", response.body()!!.toString())
                    when (res.code) {
                        1000 -> getProcess.onGetTripSuccess(res.result)
                        else -> getProcess.onGetTripFailure(res.code, res.message)
                    }
                }
            }

            //네트워크 실패
            override fun onFailure(call: Call<TripResponse2>, t: Throwable) {
                getProcess.onGetTripFailure(400, t.message.toString())
            }
        })
    }
}