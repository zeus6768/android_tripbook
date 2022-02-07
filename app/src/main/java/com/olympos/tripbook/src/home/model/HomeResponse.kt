package com.olympos.tripbook.src.home.model

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Int
    )
