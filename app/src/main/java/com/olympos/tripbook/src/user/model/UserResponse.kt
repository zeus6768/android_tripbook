package com.olympos.tripbook.src.user.model

import com.google.gson.annotations.SerializedName

data class UserResult(@SerializedName("user") val user: ArrayList<User>)

data class UserResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: UserResult?
    )
