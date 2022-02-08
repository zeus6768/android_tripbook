package com.olympos.tripbook.src.user.model

data class UserResponse(
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : UserResult?
)

data class UserResult(
    val userIdx: Int,
    val nickname: String,
    val thumbnailImageUrl: String
)