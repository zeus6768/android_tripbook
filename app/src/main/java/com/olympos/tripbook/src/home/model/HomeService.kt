package com.olympos.tripbook.src.home.model

import android.util.Log
import com.olympos.tripbook.src.trip.TripCountApi
import com.olympos.tripbook.src.trip.TripCountView
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.getUserIdx
import retrofit2.*

class HomeService {
    private lateinit var process: TripCountView

    fun setHomeProcess(process: TripCountView) {
        this.process = process
    }

    fun getTripCount() {
        Log.d("HomeService.kt", "getTripCount()")
        val homeRetrofitService = retrofit.create(TripCountApi::class.java)

        homeRetrofitService.getTripCount(getUserIdx().toString()).enqueue(object : Callback<HomeResponse> {

            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    when (res.code) {
                        1000 -> process.getTripCountSuccess(res.result)
                        else -> process.getTripCountFailure(res.code, res.message)
                    }
                }
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                Log.e("HomeServicde.kt", "getTripCount() $t")
                process.getTripCountFailure(400, t.message.toString())
            }
        })
    }
}