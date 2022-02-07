package com.olympos.tripbook.src.user.model

data class RefreshJwtResponse(
    val isValidToken: Boolean = false,
    val jwt: String = "",
    val refreshJwt: String = ""
)
