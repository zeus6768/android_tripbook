package com.olympos.tripbook.src.trip.model

import com.google.gson.annotations.SerializedName

data class TripResult(@SerializedName("trip") val trip: ArrayList<Trip>)

data class TripResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: TripResult?
    )
