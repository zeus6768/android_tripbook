package com.olympos.tripbook.src.user.model

data class RefreshJwtResponse(
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : RefreshJwtResult?
)

data class RefreshJwtResult(
    val userIdx : Int,
    val jwt : String,
    val jwtRefreshToken : String
)