package com.olympos.tripbook.src.tripcourse.model

data class Hashtag(
    var location : ArrayList<String> = ArrayList(),
    var weather : ArrayList<String> = ArrayList(),
    var feeling : ArrayList<String> = ArrayList(),
    var companion : ArrayList<String> = ArrayList(),
    var event : ArrayList<String> = ArrayList()
)