package com.olympos.tripbook.src.tripcourse.model

import com.olympos.tripbook.src.tripcourse.FALSE

data class Card (
    var index : Int = 0,
    var hasData : Int = FALSE,

    var coverImg : Int = 0,
    var title : String = "",
    var date : String = "어느 순간",
    var country : String = "나도 모르는 곳",
//    var hashtag : ArrayList<Boolean> = ArrayList(),
    var body : String = "내용이 없습니다."
)
