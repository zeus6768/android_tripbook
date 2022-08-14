package com.olympos.tripbook.src.trip.controller

import android.util.Log
import com.olympos.tripbook.src.home.model.HomeResponse
import com.olympos.tripbook.src.trip.view.GetAllTripsView
import com.olympos.tripbook.src.trip.view.GetTripCountView
import com.olympos.tripbook.src.trip.view.GetTripView
import com.olympos.tripbook.src.trip.view.PostTripView
import com.olympos.tripbook.src.trip.model.*
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.getUserIdx
import retrofit2.*

class TripApiController {

    private lateinit var getAllTripsView: GetAllTripsView
    private lateinit var getTripView: GetTripView
    private lateinit var getTripCountView: GetTripCountView
    private lateinit var postTripView: PostTripView

    fun setGetAllTripsView(getAllTripsView: GetAllTripsView) {
        this.getAllTripsView = getAllTripsView
    }

    fun setGetTripView(getTripView: GetTripView) {
        this.getTripView = getTripView
    }

    fun setGetTripCountView(getTripCountView: GetTripCountView) {
        this.getTripCountView = getTripCountView
    }

    fun setPostTripView(postTripView: PostTripView) {
        this.postTripView = postTripView
    }

    fun getAllTrips() {

        val tripRetrofitService = retrofit.create(GetAllTripsApi::class.java)

        getAllTripsView.onGetAllTripsLoading()

        tripRetrofitService.getAllTrips(getUserIdx()).enqueue(object : Callback<GetAllTripsResponse> {

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

    fun getTrip() {

        val tripRetrofitService = retrofit.create(GetTripApi::class.java)

        getTripView.onGetTripLoading()

        tripRetrofitService.getTrip(getUserIdx()).enqueue(object : Callback<GetTripResponse> {

            override fun onResponse(
                call: Call<GetTripResponse>,
                response: Response<GetTripResponse>
            ) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("TripApiController", "getTrip()")
                    when (res.code) {
                        1000 -> getTripView.onGetTripSuccess(res.result)
                        else -> getTripView.onGetTripFailure(res.code, res.message)
                    }
                }
            }

            override fun onFailure(call: Call<GetTripResponse>, t: Throwable) {
                Log.e("TripApiController", "getTrip() failure $t")
                getTripView.onGetTripFailure(400, t.message.toString())
            }
        })

    }

    fun getTripCount() {
        Log.d("TripApiController", "getTripCount()")
        val homeRetrofitService = retrofit.create(GetTripCountApi::class.java)

        homeRetrofitService.getTripCount(getUserIdx().toString()).enqueue(object : Callback<HomeResponse> {

            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    when (res.code) {
                        1000 -> getTripCountView.onGetTripCountSuccess(res.result)
                        else -> getTripCountView.onGetTripCountFailure(res.code, res.message)
                    }
                }
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                Log.e("TripApiController", "getTripCount() $t")
                getTripCountView.onGetTripCountFailure(400, t.message.toString())
            }
        })
    }

    fun postTrip(trip: Trip) {

        val tripRetrofitService = retrofit.create(PostTripApi::class.java)

        postTripView.onPostTripLoading()

        tripRetrofitService.postTrip(trip).enqueue(object : Callback<PostTripResponse> {

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

}