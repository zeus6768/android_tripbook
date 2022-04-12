package com.olympos.tripbook.utils

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

fun saveNickname(name: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("name", name)
    editor.apply()
}

fun getNickname(): String? = mSharedPreferences.getString("name", null)

fun saveUserImage(userImageURL: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("userImageURL", userImageURL)
    editor.apply()
}

fun getUserImage(): String? = mSharedPreferences.getString("userImageURL", null)

fun saveTripIdx(idx: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("tripIdx", idx)
    editor.apply()
}

fun getTripIdx(): Int = mSharedPreferences.getInt("tripIdx", 0)

fun saveDepartureYear(year: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("departureYear", year)
    editor.apply()
}

fun getDepartureYear(): Int = mSharedPreferences.getInt("departureYear", 0)

fun saveDepartureMonth(month: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("departureMonth", month)
    editor.apply()
}

fun getDepartureMonth(): Int = mSharedPreferences.getInt("departureMonth", 0)

fun saveDepartureDay(day: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("departureDay", day)
    editor.apply()
}

fun getDepartureDay(): Int = mSharedPreferences.getInt("departureDay", 0)

fun saveArrivalYear(year: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("arrivalYear", year)
    editor.apply()
}

fun getArrivalYear(): Int = mSharedPreferences.getInt("arrivalYear", 0)

fun saveArrivalMonth(month: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("arrivalMonth", month)
    editor.apply()
}

fun getArrivalMonth(): Int = mSharedPreferences.getInt("arrivalMonth", 0)

fun saveArrivalDay(day: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("arrivalDay", day)
    editor.apply()
}

fun getArrivalDay(): Int = mSharedPreferences.getInt("arrivalDay", 0)

fun logout() {
    val editor = mSharedPreferences.edit()
    editor.clear()
    editor.apply()
}