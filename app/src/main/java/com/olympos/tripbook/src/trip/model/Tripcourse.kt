package com.olympos.tripbook.src.trip.model

import com.google.gson.annotations.SerializedName

data class Tripcourse(
    @SerializedName("courseIdx") var courseIdx: Int = 0,
    @SerializedName("courseTitle") var courseTitle: String = "", //제목
    @SerializedName("courseDate") var courseDate: String = "", //여행날짜
    @SerializedName("courseComment") var courseContent: String = "", //내용
    @SerializedName("courseImg") var courseImg: Int? = null, //대표사진
)
