package com.olympos.tripbook.src.user.model

data class UserRequest(
    val kakaoID: Int = 0,
    val accessToken: String = "",
    val refreshToken: String = "",
)
