package com.olympos.tripbook.src.user.model

data class KakaoRefreshTokenResponse(
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : KakaoRefreshTokenResult?
)

data class KakaoRefreshTokenResult (
    val userIdx : Int,
    val jwt : String
)