package com.olympos.tripbook.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.olympos.tripbook.utils.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.olympos.tripbook.utils.ApplicationClass.Companion.mSharedPreferences

fun saveAccessToken(accessToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(X_ACCESS_TOKEN, accessToken)
    editor.apply()
}

fun getAccessToken(): String? = mSharedPreferences.getString(X_ACCESS_TOKEN, null)

fun saveRefreshToken(refreshToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("refreshToken", refreshToken)
    editor.apply()
}

fun getRefreshToken(): String? = mSharedPreferences.getString("refreshToken", null)

fun saveKakaoAccessToken(kakaoAccessToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("kakaoAccessToken", kakaoAccessToken)
    editor.apply()
}

fun getKakaoAccessToken(): String? = mSharedPreferences.getString("kakaoAccessToken", null)

fun saveKakaoRefreshToken(kakaoRefreshToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("kakaoRefreshToken", kakaoRefreshToken)
    editor.apply()
}

fun getKakaoRefreshToken(): String? = mSharedPreferences.getString("kakaoRefreshToken", null)

fun saveUserIdx(userIdx: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("userIdx", userIdx)
    editor.apply()
}

fun getUserIdx(): Int = mSharedPreferences.getInt("userIdx", 0)

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