package com.example.skillcheckerapp.model

data class RepositoryCreateRequest(
    val github_url: String,
    val repository_name: String,
    val branch_name: String = "main"
)