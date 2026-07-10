package com.example.skillcheckerapp.network

import com.example.skillcheckerapp.model.RepositoryResponse
import retrofit2.http.GET

interface RepositoryApi {

    @GET("repositories")
    suspend fun getRepositories():
            RepositoryResponse
}