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

fun saveRefreshJwt(refreshJwt: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("refreshJwt", refreshJwt)
    editor.apply()
}

fun getRefreshJwt(): String? = mSharedPreferences.getString("refreshJwt", null)

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