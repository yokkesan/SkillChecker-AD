package com.example.skillcheckerapp.model

data class LoginResponse(
    val token: String,
    val user: User
)