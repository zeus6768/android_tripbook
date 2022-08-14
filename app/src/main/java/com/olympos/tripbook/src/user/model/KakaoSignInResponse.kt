package com.olympos.tripbook.src.user.model

data class KakaoSignInResponse (
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : KakaoSignInResult?
)

data class KakaoSignInResult (
    val userIdx: Int,
    val accessToken: String,
    val refreshToken: String
)