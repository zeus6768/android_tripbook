package com.olympos.tripbook.src.user.model

data class SignUpResponse (
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
)