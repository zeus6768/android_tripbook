package com.olympos.tripbook.src.user.model

data class KakaoSigninResponse (
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : KakaoSigninResult?
)

data class KakaoSigninResult (
    val userIdx: Int,
    val accessToken: String,
    val refreshToken: String
)