package com.olympos.tripbook.src.user.model

data class SigninResponse(
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : SigninResult?
)

data class SigninResult(
    val userIdx: Int
)