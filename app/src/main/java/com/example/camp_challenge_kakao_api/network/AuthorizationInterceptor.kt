package com.example.week_use_kakao_api.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "KakaoAK 26273f2ac16b1aa99997bac04d1fa247"
            ).build()
        return chain.proceed(newRequest)
    }
}