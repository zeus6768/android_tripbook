package com.olympos.tripbook.src.tripcourse.controller

import com.olympos.tripbook.src.tripcourse.model.GetRecentTripCoursesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetRecentTripCoursesApi {
    @GET("/app/trip/latest/{userIdx}/courses")
    fun getRecentTripCourses(@Path("userIdx") userIdx: Int): Call<GetRecentTripCoursesResponse>
}