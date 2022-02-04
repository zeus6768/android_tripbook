package com.olympos.tripbook.src.user.model

import com.google.gson.annotations.SerializedName

data class ApiTestResult(@SerializedName("test") val user: ArrayList<ApiTest>)

data class ApiTestResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
)
