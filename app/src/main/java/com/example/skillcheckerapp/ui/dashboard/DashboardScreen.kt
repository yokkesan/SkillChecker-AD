package com.example.skillcheckerapp.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillcheckerapp.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel()
) {

    val repositories = viewModel.repositories

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Skill Checker",
            style =
                MaterialTheme
                    .typography
                    .headlineMedium
        )

        Text(
            text = "Dashboard",
            style =
                MaterialTheme
                    .typography
                    .bodyMedium
        )

        LazyColumn(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement =
                Arrangement.spacedBy(12.dp),
            contentPadding =
                PaddingValues(bottom = 24.dp)
        ) {

            items(repositories) { repository ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                ) {

                    Column(
                        modifier = Modifier.padding(
                            16.dp
                        )
                    ) {

                        Text(
                            text =
                                repository.repository_name,
                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium
                        )

                        Text(
                            text =
                                repository.language
                                    ?: "Unknown",
                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall
                        )

                        Text(
                            text =
                                "Score : ${
                                    repository.score ?: 0
                                }"
                        )
                    }
                }
            }
        }
    }
}