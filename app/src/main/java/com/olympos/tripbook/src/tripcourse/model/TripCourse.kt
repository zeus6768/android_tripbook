package com.olympos.tripbook.src.tripcourse.model

import com.google.gson.annotations.SerializedName

data class TripCourse(
    @SerializedName("courseIdx") var courseIdx: Int = 0,
    @SerializedName("courseTitle") var courseTitle: String = "", //제목
    @SerializedName("courseDate") var courseDate: String = "", //여행날짜
    @SerializedName("courseComment") var courseComment: String = "", //내용
    @SerializedName("courseImg") var courseImg: Int? = null, //대표사진
)


// 서버 JSON 형식
//@SerializedName("courseIdx") val courseIdx: Int = 0,
//@SerializedName("tripIdx") val tripIdx: Int = 0,
//@SerializedName("courseImg") val courseImg: String = "", //대표사진
//@SerializedName("courseDate") val courseDate: String = "", //여행날짜
//@SerializedName("courseTime") val courseTime: Int = 0,
//@SerializedName("courseTitle") val courseTitle: String = "", //제목
//@SerializedName("courseComment") val courseComment: String = "", //내용
//@SerializedName("cardIdx") val cardIdx: Int = 0,
//@SerializedName("status") val status: String = "",