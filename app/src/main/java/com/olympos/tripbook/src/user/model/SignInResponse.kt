package com.olympos.tripbook.src.user.model

data class SignInResponse(
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : SignInResult?
)

data class SignInResult(
    val userIdx: Int
)