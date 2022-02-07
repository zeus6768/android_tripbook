package com.olympos.tripbook.src.user.model

data class KakaoAccessTokenResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: KakaoAccessTokenResult?
)

data class KakaoAccessTokenResult (
    val userIdx: Int,
    val jwt: String
)
