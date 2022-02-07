package com.olympos.tripbook.src.tripcourse.model


import com.google.gson.annotations.SerializedName
import com.olympos.tripbook.src.tripcourse.FALSE

data class Card (@SerializedName("cardIdx") val idx: Int = 1,
                 var hasData : Int = FALSE,

                 @SerializedName("tripIdx") var tripIdx : Int =1,

                 @SerializedName("courseImg")var coverImg : String = "",
                 @SerializedName("courseTitle")var title : String = "",
                 @SerializedName("courseDate")var date : String = "어느 순간",
                 @SerializedName("courseTime")var time : Int = 2,
//                 @SerializedName("courseCo")
                 var country : String = "나도 모르는 곳",
//    var hashtag : ArrayList<Boolean> = ArrayList(),
                 @SerializedName("courseComment")var body : String = "내용이 없습니다."
)
