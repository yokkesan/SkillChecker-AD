package com.example.skillcheckerapp.network

import com.example.skillcheckerapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(
                BuildConfig.API_BASE_URL
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

    val authApi: AuthApi =
        retrofit.create(
            AuthApi::class.java
        )

    val repositoryApi: RepositoryApi =
        retrofit.create(
            RepositoryApi::class.java
        )
}