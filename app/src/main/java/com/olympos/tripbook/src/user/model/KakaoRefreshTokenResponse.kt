package com.olympos.tripbook.src.user.model

data class KakaoRefreshTokenResponse(
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : KakaoRefreshTokenResult?
)

data class KakaoRefreshTokenResult (
    val access_token : String,
    val token_type : String,
    val expires_in : Int
)