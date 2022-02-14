package com.olympos.tripbook.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.tripcourse.DateSelectDialog
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

fun getUserIdx(): Int = mSharedPreferences.getInt("userIdx", 25)

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

    return spf.getString("name", "클라우드")
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

fun saveDepartureYear(context: Context, year: Int) {
    val spf = context.getSharedPreferences("trip", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("departureYear", year)
    editor.apply()
}

fun getDepartureYear(context: Context): Int {
    val spf = context.getSharedPreferences("trip", AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("departureYear", 0)
}

fun saveDepartureMonth(context: Context, month: Int) {
    val spf = context.getSharedPreferences("trip", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("departureMonth", month)
    editor.apply()
}

fun getDepartureMonth(context: Context): Int {
    val spf = context.getSharedPreferences("trip", AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("departureMonth", 0)
}

fun saveDepartureDay(context: Context, day: Int) {
    val spf = context.getSharedPreferences("trip", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("departureDay", day)
    editor.apply()
}

fun getDepartureDay(context: Context): Int {
    val spf = context.getSharedPreferences("trip", AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("departureDay", 0)
}

fun saveArrivalYear(context: Context, year: Int) {
    val spf = context.getSharedPreferences("trip", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("arrivalYear", year)
    editor.apply()
}

fun getArrivalYear(context: Context): Int {
    val spf = context.getSharedPreferences("trip", AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("arrivalYear", 0)
}

fun saveArrivalMonth(context: Context, month: Int) {
    val spf = context.getSharedPreferences("trip", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("arrivalMonth", month)
    editor.apply()
}

fun getArrivalMonth(context: Context): Int {
    val spf = context.getSharedPreferences("trip", AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("arrivalMonth", 0)
}

fun saveArrivalDay(context: Context, day: Int) {
    val spf = context.getSharedPreferences("trip", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("arrivalDay", day)
    editor.apply()
}

fun getArrivalDay(context: Context): Int {
    val spf = context.getSharedPreferences("trip", AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("arrivalDay", 0)
}