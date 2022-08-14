package com.olympos.tripbook.src.trip.model

import com.google.gson.annotations.SerializedName

data class Trip(
    @SerializedName("tripIdx") var tripIdx: Int = 0,
    @SerializedName("userIdx") var userIdx: String = "",
    @SerializedName("themeIdx") var themeIdx: Int = 1,
    @SerializedName("tripTitle") var tripTitle: String = "",
    @SerializedName("departureDate") var departureDate: String = "",
    @SerializedName("arrivalDate") var arrivalDate: String = "",
    @SerializedName("status") var status: String = ""
)
