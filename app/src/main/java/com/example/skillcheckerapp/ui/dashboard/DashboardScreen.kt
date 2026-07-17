package com.example.skillcheckerapp.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillcheckerapp.ui.theme.DashboardAccent
import com.example.skillcheckerapp.ui.theme.DashboardBackground
import com.example.skillcheckerapp.ui.theme.DashboardCard
import com.example.skillcheckerapp.ui.theme.DashboardSubText
import com.example.skillcheckerapp.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel(),
    onSyncClick: () -> Unit = {}
) {
    val repositories =
        viewModel.repositories

    val contributions =
        repositories
            .flatMap { repository ->
                repository.contributions
                    ?.entries
                    ?: emptySet()
            }
            .groupBy { entry ->
                entry.key
            }
            .mapValues { (_, entries) ->
                entries.sumOf { entry ->
                    entry.value
                }
            }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DashboardBackground
            ),
        verticalArrangement =
            Arrangement.spacedBy(16.dp),
        contentPadding =
            PaddingValues(
                start = 16.dp,
                top = 24.dp,
                end = 16.dp,
                bottom = 32.dp
            )
    ) {
        item {
            Column(
                modifier =
                    Modifier.fillMaxWidth(),
                verticalArrangement =
                    Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Dashboard",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight =
                        FontWeight.Bold
                )

                Button(
                    onClick = onSyncClick,
                    modifier =
                        Modifier.fillMaxWidth(),
                    shape =
                        RoundedCornerShape(
                            12.dp
                        ),
                    colors =
                        ButtonDefaults
                            .buttonColors(
                                containerColor =
                                    DashboardAccent,
                                contentColor =
                                    Color.White
                            ),
                    contentPadding =
                        PaddingValues(
                            vertical = 14.dp,
                            horizontal = 24.dp
                        )
                ) {
                    Text(
                        text = "GitHubと同期",
                        fontSize = 16.sp,
                        fontWeight =
                            FontWeight.SemiBold
                    )
                }
            }
        }

        item {
            SkillScoreCard(
                repositories = repositories
            )
        }

        item {
            GithubStatsCard(
                repositories = repositories
            )
        }

        item {
            ContributionChart(
                contributions = contributions
            )
        }

        item {
            RepositoryForm(
                isRegistering =
                    viewModel.isRegistering,
                errorMessage =
                    viewModel.errorMessage,
                onRegister = {
                        githubUrl,
                        onSuccess ->

                    viewModel.createRepository(
                        githubUrl = githubUrl,
                        onSuccess = onSuccess
                    )
                }
            )
        }

        item {
            Text(
                text = "Repositories",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight =
                    FontWeight.SemiBold,
                modifier =
                    Modifier.padding(
                        top = 8.dp
                    )
            )
        }

        items(
            items = repositories,
            key = { repository ->
                repository.id
            }
        ) { repository ->
            Card(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(
                        16.dp
                    ),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            DashboardCard
                    ),
                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation =
                            8.dp
                    )
            ) {
                Column(
                    modifier =
                        Modifier.padding(
                            24.dp
                        ),
                    verticalArrangement =
                        Arrangement.spacedBy(
                            8.dp
                        )
                ) {
                    Text(
                        text =
                            repository.repository_name,
                        color = Color.White,
                        style =
                            MaterialTheme
                                .typography
                                .titleMedium,
                        fontWeight =
                            FontWeight.SemiBold
                    )

                    Text(
                        text =
                            repository.language
                                ?: "Unknown",
                        color =
                            DashboardSubText,
                        style =
                            MaterialTheme
                                .typography
                                .bodySmall
                    )

                    Text(
                        text =
                            "Score : ${
                                repository.score ?: 0
                            }",
                        color =
                            DashboardAccent,
                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium,
                        fontWeight =
                            FontWeight.SemiBold
                    )
                }
            }
        }
    }
}