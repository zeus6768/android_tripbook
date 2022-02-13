package com.olympos.tripbook.src.tripcourse_view.model

import android.util.Log
import com.olympos.tripbook.utils.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewService {

    private lateinit var tripResponseView : TripResponseView

    fun setTripResponseView(tripResponseView : TripResponseView) {
        this.tripResponseView = tripResponseView
    }

    //여행 가져오기
    fun getTrip(tripIdx : String){
        val cardRetrofitService = ApplicationClass.retrofit.create(ViewRetrofitInterface::class.java)

        tripResponseView.onGetCardsLoading()

        cardRetrofitService.getTrip(tripIdx).enqueue(object : Callback<GetTripResponse> {
            override fun onResponse(call: Call<GetTripResponse>, response: Response<GetTripResponse>) {
                Log.d("들어오는지 확인", "CardService-getTrip-onResponse")
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("__res", response.body()!!.toString())
                    when (res.code) {
                        1000 -> {
                            Log.d("REST API TEST 성공", res.toString())
                            tripResponseView.onGetCardsSuccess(res.result)
                        }

                        else -> {
                            Log.d("통신 실패 : ", res.toString())
                            tripResponseView.onGetCardsFailure(res.code, res.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<GetTripResponse>, t: Throwable) {
                Log.d("들어오는지 확인", "CardService-getTrip-onFailure")
                tripResponseView.onGetCardsFailure(400, t.message.toString())
            }
        })
    }
}