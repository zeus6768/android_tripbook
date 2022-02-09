package com.olympos.tripbook.src.tripcourse.model

import com.google.gson.annotations.SerializedName

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

//data class Cards(
//    @SerializedName("cards") val cards : ArrayList<Card> //얘 이름 확실하지 않음(이름 없는듯)
//)

data class  GetTripResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val cards : ArrayList<Card> //얘도 이름 바꿔야 함
)
