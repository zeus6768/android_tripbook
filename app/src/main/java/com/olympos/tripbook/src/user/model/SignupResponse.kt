package com.olympos.tripbook.src.user.model

data class SignupResponse (
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
)