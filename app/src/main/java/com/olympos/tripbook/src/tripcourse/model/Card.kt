package com.olympos.tripbook.src.tripcourse.model

data class Card (
    var cardDate : String = "어느 순간",
    var cardCountry : String = "나도 모르는 곳",
    var cardTitle : String = "",
    var hashtag : ArrayList<Boolean> = ArrayList(),
    var body : String = "내용이 없습니다."
)
