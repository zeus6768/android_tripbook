package com.olympos.tripbook.src.tripcourse_view.model

import com.google.gson.annotations.SerializedName
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.tripcourse.model.Card

data class GetTripResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : ArrayList<Card> //얘도 이름 바꿔야 함
)

data class GetRecentTripResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : Trip
)

data class GetRecentTripCardsResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : ArrayList<Card>
)