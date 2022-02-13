package com.olympos.tripbook.src.tripcourse.model


import com.google.gson.annotations.SerializedName
import com.olympos.tripbook.src.tripcourse.FALSE

data class Card (
    //관리 데이터(유저와 상관 x)
    @SerializedName("tripIdx") var tripIdx : Int = 0,
    @SerializedName("cardIdx") var idx: Int = 0,

    var hasData : Int = FALSE,

    @SerializedName("courseImg") var coverImg : String = "",
    @SerializedName("courseDate") var date : String = "날짜를 선택해주세요",
    @SerializedName("courseTime") var time : Int = 2,
    @SerializedName("courseTitle") var title : String = "",
    @SerializedName("courseComment")var body : String = "내용이 없습니다.",

    @SerializedName("latitude") var latitude : String? = null,
    @SerializedName("longitude") var longitude : String? = null
//    @SerializedName("courseCountry")
//    var country : String = "나도 모르는 곳",
//    var hashtag : ArrayList<Boolean> = ArrayList(),
)
