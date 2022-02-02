package com.olympos.tripbook.src.trip.model

import androidx.room.*

@Entity(tableName = "TripTable")
data class Trip(@PrimaryKey(autoGenerate = true) val idx: Int = 0,
    var title: String = "", //제목
    var departureDate: String = "", //출발일
    var arrivalDate: String = "", //도착일
    var theme: Int = 1, //테마
)
