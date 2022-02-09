package com.olympos.tripbook.src.tripcourse.model

import com.google.gson.annotations.SerializedName

data class CardResult(
    @SerializedName("card") val card: ArrayList<Card>
)

data class PostCardResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
//    @SerializedName("result") val card : Card
)

data class GetCardResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val cards : ArrayList<Card>?
)
