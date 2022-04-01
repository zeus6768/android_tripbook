package com.olympos.tripbook.src.user.model

import com.google.gson.annotations.SerializedName

data class UpdateKakaoAccessTokenResponse (
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : UpdateKakaoAccessTokenResult?
)

data class UpdateKakaoAccessTokenResult (
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Int
)