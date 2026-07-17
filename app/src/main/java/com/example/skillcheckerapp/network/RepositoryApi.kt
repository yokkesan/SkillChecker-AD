package com.example.skillcheckerapp.network

import com.example.skillcheckerapp.model.RepositoryCreateRequest
import com.example.skillcheckerapp.model.RepositoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RepositoryApi {

    @GET("repositories")
    suspend fun getRepositories():
            RepositoryResponse

    @POST("repositories")
    suspend fun createRepository(
        @Body request: RepositoryCreateRequest
    )
}