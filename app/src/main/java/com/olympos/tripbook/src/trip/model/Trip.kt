package com.olympos.tripbook.src.trip.model

data class Trip(
    val userIdx: String = "",
    var tripTitle: String = "", //제목
    var departureDate: String = "", //출발일
    var arrivalDate: String = "", //도착일
    var themeIdx: Int = 1, //테마
//tripIdx가 있어야 하는 것이 아닌가, date type말고 String type은 어떠한지?
)
