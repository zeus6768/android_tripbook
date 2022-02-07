package com.olympos.tripbook.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.olympos.tripbook.utils.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.olympos.tripbook.utils.ApplicationClass.Companion.mSharedPreferences

fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(X_ACCESS_TOKEN, jwtToken)

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString(X_ACCESS_TOKEN, null)

fun saveUserIdx(idx: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("userIdx", idx)

    editor.apply()
}

fun getUserIdx(): Int = mSharedPreferences.getInt("userIdx", 3)

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

fun saveTripIdx(context: Context, idx: Int) {
    val spf = context.getSharedPreferences("tripIdx", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("tripIdx", idx)
    editor.apply()
}

fun getTripIdx(context: Context): Int {
    val spf = context.getSharedPreferences("tripIdx", AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("tripIdx", 0)
}