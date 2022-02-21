package com.olympos.tripbook.src.user.model

data class updateKakaoAccessTokenResponse (
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : updateKakaoAccessTokenResult?
)

data class updateKakaoAccessTokenResult (
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int
)