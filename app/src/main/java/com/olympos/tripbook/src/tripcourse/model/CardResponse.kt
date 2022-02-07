package com.olympos.tripbook.src.tripcourse.model

import com.google.gson.annotations.SerializedName

data class CardResult(@SerializedName("card") val card: ArrayList<Card>)

data class CardResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    )
