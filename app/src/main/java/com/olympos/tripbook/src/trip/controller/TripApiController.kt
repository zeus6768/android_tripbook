package com.olympos.tripbook.src.trip.controller

import android.util.Log
import com.olympos.tripbook.src.trip.model.*
import com.olympos.tripbook.src.trip.view.GetAllTripsView
import com.olympos.tripbook.src.trip.view.GetRecentTripView
import com.olympos.tripbook.src.trip.view.GetTripCountView
import com.olympos.tripbook.src.trip.view.PostTripView
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.getUserIdx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TripApiController {

    private lateinit var getRecentTripView: GetRecentTripView
    private lateinit var getTripCountView: GetTripCountView
    private lateinit var postTripView: PostTripView
    private lateinit var getAllTripsView: GetAllTripsView

    fun setRecentTripView(view: GetRecentTripView) {
        this.getRecentTripView = view
    }

    fun setTripCountView(view: GetTripCountView) {
        this.getTripCountView = view
    }

    fun setPostTripView(view: PostTripView) {
        this.postTripView = view
    }

    fun setAllTripsView(view: GetAllTripsView) {
        this.getAllTripsView = view
    }

    // 1-1. 최근 여행 조회
    fun getRecentTrip() {

        val retrofit = retrofit.create(GetRecentTripApi::class.java)

        retrofit.getRecentTrip(getUserIdx()).enqueue(object : Callback<GetRecentTripResponse> {

            override fun onResponse(
                call: Call<GetRecentTripResponse>,
                response: Response<GetRecentTripResponse>
            ) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("TripApiController", "getTrip()")
                    when (res.code) {
                        1000 -> getRecentTripView.onGetRecentTripSuccess(res.result)
                        else -> getRecentTripView.onGetRecentTripFailure(res.code, res.message)
                    }
                }
            }

            override fun onFailure(call: Call<GetRecentTripResponse>, t: Throwable) {
                Log.e("TripApiController", "getTrip() failure $t")
                getRecentTripView.onGetRecentTripFailure(400, t.message.toString())
            }
        })

    }

    // 1-3. 전체 여행 수 조회 API
    fun getTripCount() {
        Log.d("TripApiController", "getTripCount()")
        val retrofit = retrofit.create(GetTripCountApi::class.java)

        retrofit.getTripCount(getUserIdx().toString()).enqueue(object : Callback<GetTripCountResponse> {

            override fun onResponse(call: Call<GetTripCountResponse>, response: Response<GetTripCountResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    when (res.code) {
                        1000 -> getTripCountView.onGetTripCountSuccess(res.result)
                        else -> getTripCountView.onGetTripCountFailure(res.code, res.message)
                    }
                }
            }

            override fun onFailure(call: Call<GetTripCountResponse>, t: Throwable) {
                Log.e("TripApiController", "getTripCount() $t")
                getTripCountView.onGetTripCountFailure(400, t.message.toString())
            }
        })
    }

    // 2-3. 여행 생성
    fun postTrip(trip: Trip) {

        val retrofit = retrofit.create(PostTripApi::class.java)

        postTripView.onPostTripLoading()

        retrofit.postTrip(trip).enqueue(object : Callback<PostTripResponse> {

            override fun onResponse(
                call: Call<PostTripResponse>,
                response: Response<PostTripResponse>
            ) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("TripApiController", "postTrip()")
                    when (res.code) {
                        1000 -> postTripView.onPostTripSuccess(res.result)
                        else -> postTripView.onPostTripFailure(res.code, res.message)
                    }
                }
            }

            override fun onFailure(call: Call<PostTripResponse>, t: Throwable) {
                Log.e("TripApiController", "postTrip() failure $t")
                postTripView.onPostTripFailure(400, t.message.toString())
            }
        })
    }

    // 3-1. 유저 전체 여행 조회
    fun getAllTrips() {

        val retrofit = retrofit.create(GetAllTripsApi::class.java)

        retrofit.getAllTrips(getUserIdx()).enqueue(object : Callback<GetAllTripsResponse> {

            override fun onResponse(
                call: Call<GetAllTripsResponse>,
                response: Response<GetAllTripsResponse>
            ) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("TripApiController", "getAllTrips()")
                    when (res.code) {
                        1000 -> getAllTripsView.onGetAllTripsSuccess(res.result)
                        else -> getAllTripsView.onGetAllTripsFailure(res.code, res.message)
                    }
                }
            }

            override fun onFailure(call: Call<GetAllTripsResponse>, t: Throwable) {
                Log.e("TripApiController", "getAllTrips() failure $t")
                getAllTripsView.onGetAllTripsFailure(400, t.message.toString())
            }

        })

    }

}