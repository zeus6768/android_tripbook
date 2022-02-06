package com.olympos.tripbook.utils

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.olympos.tripbook.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationClass : Application() {
    companion object {
        const val TAG: String = "Tripbook-Android"
        const val BASE_URL = "https://www.tripbook.shop"

        lateinit var retrofit: Retrofit
    }
    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        KakaoSdk.init(this,getString(R.string.kakao_app_key))
    }
}