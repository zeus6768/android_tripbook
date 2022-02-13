package com.olympos.tripbook.src.home.model

import com.google.gson.annotations.SerializedName
import com.olympos.tripbook.src.tripcourse.model.Card

data class HomeResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Int
    )

data class RecentTripResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : ArrayList<Card>
)