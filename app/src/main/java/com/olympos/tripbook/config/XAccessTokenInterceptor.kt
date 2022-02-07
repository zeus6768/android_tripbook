package com.olympos.tripbook.config

import com.olympos.tripbook.utils.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.olympos.tripbook.utils.getJwt
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class XAccessTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        val jwtToken: String? = getJwt()

        jwtToken?.let{
            builder.addHeader(X_ACCESS_TOKEN, jwtToken)
        }

        return chain.proceed(builder.build())
    }
}