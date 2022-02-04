package com.olympos.tripbook.src.user.model

import com.google.gson.annotations.SerializedName

data class UserResult(@SerializedName("user") val user: ArrayList<User>)

data class UserResponse(
    @SerializedName("isSuccess") val isValidToken: Boolean,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("thumbnailImageUrl") val thumbnailImageUrl: String,
)
