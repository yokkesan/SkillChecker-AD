package com.example.skillcheckerapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcheckerapp.model.Repository
import com.example.skillcheckerapp.network.RetrofitClient
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    var repositories by mutableStateOf(
        listOf<Repository>()
    )

    var errorMessage by mutableStateOf<String?>(null)

    init {
        fetchRepositories()
    }

    private fun fetchRepositories() {

        viewModelScope.launch {

            try {

                repositories =
                    RetrofitClient
                        .repositoryApi
                        .getRepositories()
                        .repositories

            } catch (e: Exception) {

                errorMessage =
                    e.message

                e.printStackTrace()
            }
        }
    }
}