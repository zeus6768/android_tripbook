package com.olympos.tripbook.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.kakao.sdk.common.KakaoSdk
import com.olympos.tripbook.R
import com.olympos.tripbook.config.XAccessTokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object {
        const val X_ACCESS_TOKEN: String = "x-access-token"
        const val TAG: String = "TRIPBOOK-APP"
        const val BASE_URL = "https://www.tripbook.shop"

        enum class DateUnit{
            YEAR, MONTH, DAY
        }

        lateinit var mSharedPreferences: SharedPreferences
        lateinit var retrofit: Retrofit
        lateinit var retrofitWithoutAccessToken: Retrofit

        fun dateToKorean(date: String, option: DateUnit): String{

            val dateArr = date.split("-")
            val result = when (option) {
                DateUnit.DAY -> dateArr[0] + "년 " + dateArr[1].toInt().toString() + "월 " + dateArr[2].toInt().toString() + "일"
                DateUnit.MONTH -> dateArr[0] + "년 " + dateArr[1].toInt().toString() + "월"
                DateUnit.YEAR -> dateArr[0] + "년"
            }

            return result
        }

        fun generatePeriod(start: String, end: String): String = dateToKorean(start, DateUnit.DAY) + " ~ " + dateToKorean(end, DateUnit.DAY)

    }
    override fun onCreate() {
        super.onCreate()

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor())
            .build()

        val clientWithoutAccessToken: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitWithoutAccessToken = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientWithoutAccessToken)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mSharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)
        KakaoSdk.init(this,getString(R.string.kakao_app_key))
    }
}