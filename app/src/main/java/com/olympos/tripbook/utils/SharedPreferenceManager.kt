package com.olympos.tripbook.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun saveJwt(context: Context, jwt: String) {
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("jwt", jwt)
    editor.apply()
}

fun getJwt(context: Context): String? {
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("jwt", "")
}

fun saveTokenValidity(context: Context, isValidToken: Boolean) {
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putBoolean("accessToken", isValidToken)
    editor.apply()
}

fun getTokenValidity(context: Context): Boolean {
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

    return spf.getBoolean("isValidToken", false)
}

fun saveNickname(context: Context, name: String) {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("name", name)
    editor.apply()
}

fun getNickname(context: Context): String? {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("name", "")
}

fun saveProfileImageURL(context: Context, url: String) {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("profileImageUrl", url)
    editor.apply()
}

fun getProfileImageURL(context: Context): String? {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("profileImageUrl", "")
}