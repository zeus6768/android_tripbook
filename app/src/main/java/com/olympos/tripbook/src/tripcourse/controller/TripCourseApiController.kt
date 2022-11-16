package com.olympos.tripbook.src.tripcourse.controller

import android.util.Log
import com.olympos.tripbook.src.tripcourse.model.GetRecentTripCoursesResponse
import com.olympos.tripbook.src.tripcourse.model.GetTripCoursesResponse
import com.olympos.tripbook.src.tripcourse.view.GetRecentTripCoursesView
import com.olympos.tripbook.src.tripcourse.view.GetTripCoursesView
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.getUserIdx
import retrofit2.*

class TripCourseApiController {

    private lateinit var getRecentTripCoursesView: GetRecentTripCoursesView
    private lateinit var getTripCoursesView: GetTripCoursesView

    fun setRecentTripCourseView(view: GetRecentTripCoursesView) {
        this.getRecentTripCoursesView = view
    }

    fun setTripCourseView(view: GetTripCoursesView) {
        this.getTripCoursesView = view
    }

    // 1-2. 최근 여행 발자국 조회 API
    fun getRecentTripCourses() {

        val retrofit = retrofit.create(GetRecentTripCoursesApi::class.java)
        retrofit.getRecentTripCourses(getUserIdx()).enqueue(object : Callback<GetRecentTripCoursesResponse> {
            override fun onResponse(
                call: Call<GetRecentTripCoursesResponse>,
                response: Response<GetRecentTripCoursesResponse>
            ) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("TripCourseApiController", "getRecentTripCourses()")
                    when (res.code) {
                        1000 -> getRecentTripCoursesView.onGetRecentTripCoursesSuccess(res.result)
                        else -> getRecentTripCoursesView.onGetRecentTripCoursesFailure(res.code, res.message)
                    }
                }
            }

            override fun onFailure(call: Call<GetRecentTripCoursesResponse>, t: Throwable) {
                Log.e("TripCourseApiController", "getRecentTripCourses() failure $t")
                getRecentTripCoursesView.onGetRecentTripCoursesFailure(400, t.message.toString())
            }
        })

    }

    // 2-2. 특정 발자국 조회
    fun geTripCourses(tripIdx: Int) {

        val retrofit = retrofit.create(GetTripCoursesApi::class.java)
        retrofit.getTripCourses(getUserIdx(), tripIdx).enqueue(object : Callback<GetTripCoursesResponse> {
            override fun onResponse(
                call: Call<GetTripCoursesResponse>,
                response: Response<GetTripCoursesResponse>
            ) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("TripCourseApiController", "geTripCourses()")
                    when (res.code) {
                        1000 -> getTripCoursesView.onGetTripCoursesSuccess(res.result)
                        else -> getTripCoursesView.onGetTripCoursesFailure(res.code, res.message)
                    }
                }
            }

            override fun onFailure(call: Call<GetTripCoursesResponse>, t: Throwable) {
                Log.e("TripCourseApiController", "geTripCourses() failure $t")
                getTripCoursesView.onGetTripCoursesFailure(400, t.message.toString())
            }
        })

    }
}