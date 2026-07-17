package com.example.skillcheckerapp.network

import com.example.skillcheckerapp.model.LoginRequest
import com.example.skillcheckerapp.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}