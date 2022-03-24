package com.olympos.tripbook.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.olympos.tripbook.R
import com.olympos.tripbook.config.XAccessTokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object {
        const val X_ACCESS_TOKEN: String = "x-access-token"         // JWT Token Key
        const val TAG: String = "TRIPBOOK-APP"
        const val BASE_URL = "https://www.tripbook.shop"

        lateinit var mSharedPreferences: SharedPreferences
        lateinit var retrofit: Retrofit
        lateinit var retrofitWithoutAccessToken: Retrofit
    }
    override fun onCreate() {
        super.onCreate()

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        val clientWithoutAccessToken: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
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