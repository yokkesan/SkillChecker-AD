package com.example.skillcheckerapp.model

data class Repository(

    val id: Long,

    val repository_name: String,

    val github_url: String,

    val branch_name: String?,

    val status: String?,

    val language: String?,

    val description: String?,

    val stargazers_count: Int?,

    val forks_count: Int?,

    val score: Int?
)