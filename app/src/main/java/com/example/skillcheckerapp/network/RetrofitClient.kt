package com.example.skillcheckerapp.network

import android.content.Context
import com.example.skillcheckerapp.BuildConfig
import com.example.skillcheckerapp.security.TokenManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private lateinit var tokenManager: TokenManager

    fun initialize(
        context: Context
    ) {
        if (
            !::tokenManager.isInitialized
        ) {
            tokenManager =
                TokenManager(
                    context.applicationContext
                )
        }
    }

    private val okHttpClient: OkHttpClient by lazy {
        check(
            ::tokenManager.isInitialized
        ) {
            "RetrofitClient.initialize(context) must be called before using the API."
        }

        OkHttpClient.Builder()
            .addInterceptor(
                AuthInterceptor(
                    tokenManager
                )
            )
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(
                BuildConfig.API_BASE_URL
            )
            .client(
                okHttpClient
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    val authApi: AuthApi by lazy {
        retrofit.create(
            AuthApi::class.java
        )
    }

    val repositoryApi: RepositoryApi by lazy {
        retrofit.create(
            RepositoryApi::class.java
        )
    }

    fun getTokenManager(): TokenManager {
        check(
            ::tokenManager.isInitialized
        ) {
            "RetrofitClient.initialize(context) must be called first."
        }

        return tokenManager
    }
}