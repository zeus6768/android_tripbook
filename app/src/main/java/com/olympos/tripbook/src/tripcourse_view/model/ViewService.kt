package com.olympos.tripbook.src.tripcourse_view.model

import android.util.Log
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.utils.ApplicationClass
import com.olympos.tripbook.utils.getUserIdx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewService {

    private lateinit var tripResponseView : TripResponseView
    private lateinit var recentTripResponseView : RecentTripResponseView
    private lateinit var recentTripCardsResponseView : RecentTripCardsResponseView

    fun setTripResponseView(tripResponseView : TripResponseView) {
        this.tripResponseView = tripResponseView
    }

    fun setRecentTripResponseView(recentTripResponseView: RecentTripResponseView) {
        this.recentTripResponseView = recentTripResponseView

    }

    fun setRecentTripCardsResponseView(recentTripCardsResponseView: RecentTripCardsResponseView) {
        this.recentTripCardsResponseView = recentTripCardsResponseView

    }

    //여행 가져오기
    fun getTrip(tripIdx : Int){

        val userIdx = getUserIdx()
        val cardRetrofitService = ApplicationClass.retrofit.create(ViewRetrofitInterface::class.java)

        tripResponseView.onGetCardsLoading()

        cardRetrofitService.getTrip(userIdx, tripIdx).enqueue(object : Callback<GetTripResponse> {
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

    fun getRecentTrip(){
        Log.d("WhereIsProblem", "getRecentTrip")
        val userIdx = getUserIdx()
        val cardRetrofitService = ApplicationClass.retrofit.create(ViewRetrofitInterface::class.java)

        recentTripResponseView.onGetRecentTripLoading()

        cardRetrofitService.getRecentTrip(userIdx).enqueue(object : Callback<GetRecentTripResponse> {
            override fun onResponse(call: Call<GetRecentTripResponse>, response: Response<GetRecentTripResponse>) {
                Log.d("들어오는지 확인", "ViewService-getRecentTrip-onResponse")
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("__res", response.body()!!.toString())
                    when (res.code) {
                        1000 -> {
                            Log.d("REST API TEST 성공", res.toString())
                            recentTripResponseView.onGetRecentTripSuccess(res.result as Trip)
                        }

                        else -> {
                            Log.d("통신 실패 : ", res.toString())
                            recentTripResponseView.onGetRecentTripFailure(res.code, res.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<GetRecentTripResponse>, t: Throwable) {
                Log.d("들어오는지 확인", "ViewService-getRecentTrip-onFailure")
                recentTripResponseView.onGetRecentTripFailure(400, t.message.toString())
            }
        })
    }

    fun getRecentTripCards(){
        Log.d("WhereIsProblem", "getRecentTripCards")

        val userIdx = getUserIdx()
        val cardRetrofitService = ApplicationClass.retrofit.create(ViewRetrofitInterface::class.java)

        recentTripCardsResponseView.onGetRecentTripCardsLoading()

        cardRetrofitService.getRecentTripCards(userIdx).enqueue(object : Callback<GetRecentTripCardsResponse> {
            override fun onResponse(call: Call<GetRecentTripCardsResponse>, response: Response<GetRecentTripCardsResponse>) {
                Log.d("들어오는지 확인", "ViewService-getRecentTripCards-onResponse")
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("__res", response.body()!!.toString())
                    when (res.code) {
                        1000 -> {
                            Log.d("REST API TEST 성공", res.toString())
                            recentTripCardsResponseView.onGetRecentTripCardsSuccess(res.result)
                        }

                        else -> {
                            Log.d("통신 실패 : ", res.toString())
                            recentTripCardsResponseView.onGetRecentTripCardsFailure(res.code, res.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<GetRecentTripCardsResponse>, t: Throwable) {
                Log.d("들어오는지 확인", "ViewService-getRecentTripCards-onFailure")
                recentTripCardsResponseView.onGetRecentTripCardsFailure(400, t.message.toString())
            }
        })
    }
}