package com.olympos.tripbook.src.trip.model

data class Trip(
    var userIdx: String = "",
    var tripTitle: String = "", //제목
    var departureDate: String = "", //출발일
    var arrivalDate: String = "", //도착일
    var themeIdx: Int = 1, //테마
)
