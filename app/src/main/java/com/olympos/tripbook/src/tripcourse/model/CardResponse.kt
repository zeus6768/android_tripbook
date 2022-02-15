package com.olympos.tripbook.src.tripcourse.model

import com.google.gson.annotations.SerializedName

data class PostCardResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val courseIdx : Int
)

data class ServerDefaultResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)

data class GetCardResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val cards : ArrayList<Card>?
)

data class GetTripcourseResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : ArrayList<Card> //얘도 이름 바꿔야 함
)

//data class CardResponse(
//    @SerializedName("courseIdx") val courseIdx :Int,
//    @SerializedName("tripIdx") val tripIdx : Int,
//    @SerializedName("cardIdx") val cardIdx : Int,
//    @SerializedName("courseImg") val courseImg : String,
//    @SerializedName("courseDate") val courseDate : String,
//    @SerializedName("courseTime") val courseTime : Int,
//    @SerializedName("courseTitle") val courseTitle : String,
//    @SerializedName("courseComment") val courseComment : String,
//    @SerializedName("latitude") val latitude : String?,
//    @SerializedName("longitude") val longitude : String?
//    @SerializedName("status") val status : String
//)
