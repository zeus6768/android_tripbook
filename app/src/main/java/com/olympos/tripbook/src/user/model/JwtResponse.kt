package com.olympos.tripbook.src.user.model

data class JwtResponse(
    val code: Int = 0,
    val nickname: String = "",
    val thumbnailImageUrl: String = ""
)
