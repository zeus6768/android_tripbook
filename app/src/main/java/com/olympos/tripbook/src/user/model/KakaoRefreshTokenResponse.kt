package com.olympos.tripbook.src.user.model

data class KakaoRefreshTokenResponse(
    val isValidToken: Boolean = false,
    val accessToken: String = "",
    val kakaoRefreshToken: String = ""
)