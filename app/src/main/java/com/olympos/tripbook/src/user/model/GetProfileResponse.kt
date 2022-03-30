package com.olympos.tripbook.src.user.model

data class GetProfileResponse (
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result: GetProfileResult?
)

data class GetProfileResult (
    val userProfileIdx: Int,
    val userIdx: Int,
    val nickName: String,
    val userImg: String,
    val comment: String,
    val createAt: String,
    val updateAt: String,
    val status: String,
)