package com.example.skillcheckerapp.network

import com.example.skillcheckerapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val repositoryApi: RepositoryApi =
        Retrofit.Builder()
            .baseUrl(
                BuildConfig.API_BASE_URL
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(
                RepositoryApi::class.java
            )
}