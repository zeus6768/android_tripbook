package com.olympos.tripbook.src.user.model

data class UpdateKakaoAccessTokenResponse (
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : UpdateKakaoAccessTokenResult?
)

data class UpdateKakaoAccessTokenResult (
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int
)