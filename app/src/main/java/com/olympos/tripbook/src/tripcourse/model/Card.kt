package com.olympos.tripbook.src.tripcourse.model

data class Card (
    var cardDate : String = "",
    var cardCountry : String = "",
    var cardTitle : String = "",
    var hashtag : ArrayList<Boolean> = ArrayList(),
    var body : String = ""
)
