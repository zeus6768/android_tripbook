package com.olympos.tripbook.src.home.model

import android.util.Log
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.getUserIdx
import retrofit2.*

class HomeService {
    private lateinit var process: HomeGetProcess

    fun setProcess(process: HomeGetProcess) {
        this.process = process
    }

    private lateinit var cardsView : CardsView

    fun setCardsView(cardsView : CardsView) {
        this.cardsView = cardsView
    }

    fun getTripCount() {
        Log.d("들어오는지 확인", "HomeService-getTripCount")
        val homeRetrofitService = retrofit.create(HomeRetrofitInterface::class.java)

        //호출 전 loading
        process.onGetHomeLoading()

        //api 호출 후 응답받으면 callback
        homeRetrofitService.getTripCount(getUserIdx().toString()).enqueue(object : Callback<HomeResponse> {
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

    //여행 가져오기
    fun getTrip(tripIdx : String){
        val homeRetrofitService = retrofit.create(HomeRetrofitInterface::class.java)

        cardsView.onGetCardsLoading()

        homeRetrofitService.getTrip(tripIdx).enqueue(object : Callback<RecentTripResponse> {
            override fun onResponse(call: Call<RecentTripResponse>, response: Response<RecentTripResponse>) {
                Log.d("들어오는지 확인", "HomeService-getTrip-onResponse")
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("__res", response.body()!!.toString())
                    when (res.code) {
                        1000 -> {
                            Log.d("REST API TEST 성공", res.toString())
                            cardsView.onGetCardsSuccess(res.result)
                        }

                        else -> {
                            Log.d("통신 실패 : ", res.toString())
                            cardsView.onGetCardsFailure(res.code, res.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<RecentTripResponse>, t: Throwable) {
                Log.d("들어오는지 확인", "CardService-getTrip-onFailure")
                cardsView.onGetCardsFailure(400, t.message.toString())
            }
        })
    }
}