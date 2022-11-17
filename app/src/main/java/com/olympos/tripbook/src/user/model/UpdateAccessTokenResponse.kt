package com.olympos.tripbook.src.user.model

data class UpdateAccessTokenResponse (
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result: UpdateAccessTokenResult?
)

data class UpdateAccessTokenResult (
    val userIdx: Int,
    val accessToken: String,
    val refreshToken: String
)
