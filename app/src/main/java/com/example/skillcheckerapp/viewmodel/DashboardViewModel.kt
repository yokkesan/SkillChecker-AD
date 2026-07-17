package com.example.skillcheckerapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcheckerapp.model.Repository
import com.example.skillcheckerapp.model.RepositoryCreateRequest
import com.example.skillcheckerapp.network.RetrofitClient
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    var repositories by mutableStateOf(
        listOf<Repository>()
    )
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isRegistering by mutableStateOf(false)
        private set

    init {
        fetchRepositories()
    }

    fun fetchRepositories() {
        viewModelScope.launch {
            try {
                errorMessage = null

                repositories =
                    RetrofitClient
                        .repositoryApi
                        .getRepositories()
                        .repositories

            } catch (e: Exception) {
                errorMessage =
                    e.message
                        ?: "Repository一覧の取得に失敗しました。"

                e.printStackTrace()
            }
        }
    }

    fun createRepository(
        githubUrl: String,
        onSuccess: () -> Unit
    ) {
        val normalizedUrl =
            githubUrl.trim().removeSuffix("/")

        val repositoryName =
            normalizedUrl
                .substringAfterLast("/")
                .removeSuffix(".git")

        if (
            !normalizedUrl.startsWith(
                "https://github.com/"
            ) ||
            repositoryName.isBlank()
        ) {
            errorMessage =
                "正しいGitHubリポジトリURLを入力してください。"

            return
        }

        if (isRegistering) {
            return
        }

        viewModelScope.launch {
            isRegistering = true
            errorMessage = null

            try {
                RetrofitClient
                    .repositoryApi
                    .createRepository(
                        RepositoryCreateRequest(
                            github_url =
                                normalizedUrl,
                            repository_name =
                                repositoryName,
                            branch_name =
                                "main"
                        )
                    )

                fetchRepositories()

                onSuccess()

            } catch (e: Exception) {
                errorMessage =
                    e.message
                        ?: "Repositoryの登録に失敗しました。"

                e.printStackTrace()

            } finally {
                isRegistering = false
            }
        }
    }

    fun clearErrorMessage() {
        errorMessage = null
    }
}