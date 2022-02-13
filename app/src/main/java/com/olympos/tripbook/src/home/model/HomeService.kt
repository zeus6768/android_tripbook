package com.olympos.tripbook.src.home.model

import android.util.Log
import com.olympos.tripbook.src.trip.model.TripPostProcess
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.getUserIdx
import retrofit2.*

class HomeService {
    private lateinit var process: HomeGetProcess

    fun setProcess(process: HomeGetProcess) {
        this.process = process
    }

    fun getTripCount() {
        Log.d("들어오는지 확인", "HomeService-getTripCount")
        val homeRetrofitService = retrofit.create(HomeRetrofitInterface::class.java)

        //호출 전 loading
        process.onGetHomeLoading()

        //api 호출 후 응답받으면 callback
        homeRetrofitService.getTripCount(getUserIdx()).enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("__res", response.body()!!.toString())
                    when (res.code) {
                        1000 -> process.onGetHomeSuccess(res.result)
                        else -> process.onGetHomeFailure(res.code, res.message)
                    }
                }
            }

            //네트워크 실패
            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                process.onGetHomeFailure(400, t.message.toString())
            }
        })
    }
}