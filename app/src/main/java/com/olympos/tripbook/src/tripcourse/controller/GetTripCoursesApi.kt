package com.olympos.tripbook.src.tripcourse.controller

import com.olympos.tripbook.src.tripcourse.model.GetTripCoursesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetTripCoursesApi {
    @GET("/app/trip/latest/{userIdx}/courses")
    fun getTripCourses(@Path("userIdx") userIdx: Int): Call<GetTripCoursesResponse>
}