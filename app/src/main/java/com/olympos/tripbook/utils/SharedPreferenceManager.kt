package com.olympos.tripbook.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun saveJwt(context: Context, jwt: Int) {
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("jwt", jwt)
    editor.apply()
}

fun getJwt(context: Context): Int {
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("jwt", 0)!!
}

fun saveNickname(context: Context, name: String) {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("name", name)
    editor.apply()
}

fun getNickname(context: Context): String {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("name", "")!!
}

fun saveProfileImageURL(context: Context, url: String) {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("profileImageUrl", url)
    editor.apply()
}

fun getProfileImageURL(context: Context): String {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("profileImageUrl", "")!!
}

fun saveAccessToken(context: Context, accessToken: String) {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("accessToken", accessToken)
    editor.apply()
}

fun getAccessToken(context: Context): String {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("accessToken", "")!!
}

fun saveRefreshToken(context: Context, refreshToken: String) {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("refreshToken", refreshToken)
    editor.apply()
}

fun getRefreshToken(context: Context): String {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("refreshToken", "")!!
}