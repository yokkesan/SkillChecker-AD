package com.example.skillcheckerapp.network

import com.example.skillcheckerapp.security.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        val originalRequest =
            chain.request()

        val requestBuilder =
            originalRequest
                .newBuilder()
                .header(
                    "Accept",
                    "application/json"
                )

        val token =
            tokenManager.getToken()

        if (
            !token.isNullOrBlank()
        ) {
            requestBuilder.header(
                "Authorization",
                "Bearer $token"
            )
        }

        return chain.proceed(
            requestBuilder.build()
        )
    }
}