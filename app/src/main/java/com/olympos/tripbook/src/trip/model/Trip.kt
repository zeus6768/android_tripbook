package com.olympos.tripbook.src.trip.model

import com.google.gson.annotations.SerializedName

data class Trip(
    var isChange: Boolean = false,

    @SerializedName("tripIdx") var tripIdx: Int = 0,
    @SerializedName("userIdx") var userIdx: String = "",
    @SerializedName("tripTitle") var tripTitle: String = "", //제목
    @SerializedName("departureDate") var departureDate: String = "", //출발일
    @SerializedName("arrivalDate") var arrivalDate: String = "", //도착일
    @SerializedName("themeIdx") var themeIdx: Int = 1, //테마
)
