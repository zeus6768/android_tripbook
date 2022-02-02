package com.olympos.tripbook.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun saveJwt(context: Context, jwt: String) {
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("jwt", jwt)
    editor.apply()
}

fun getJwt(context: Context): String {
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("jwt", "")!!
}

fun saveUserName(context: Context, name: String) {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("name", name)
    editor.apply()
}

fun getUserName(context: Context): String {
    val spf = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("name", "")!!
}
