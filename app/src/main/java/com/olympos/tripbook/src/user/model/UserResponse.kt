package com.olympos.tripbook.src.user.model

data class UserResponse(
    val isValidToken: Boolean,
    val nickname: String,
    val thumbnailImageUrl: String,
)
