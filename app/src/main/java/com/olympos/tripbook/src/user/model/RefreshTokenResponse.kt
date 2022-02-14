package com.olympos.tripbook.src.user.model

data class RefreshTokenResponse(
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : RefreshTokenResult?
)

data class RefreshTokenResult(
    val userIdx : Int,
    val accessToken : String,
    val refreshToken : String
)