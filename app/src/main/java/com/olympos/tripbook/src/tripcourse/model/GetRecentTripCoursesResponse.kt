package com.olympos.tripbook.src.tripcourse.model

import com.google.gson.annotations.SerializedName

data class GetRecentTripCoursesResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<TripCourse>
)
