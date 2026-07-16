package com.example.skillcheckerapp.ui.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillcheckerapp.model.Repository
import com.example.skillcheckerapp.ui.theme.DashboardAccent
import com.example.skillcheckerapp.ui.theme.DashboardBackground
import com.example.skillcheckerapp.ui.theme.DashboardBorder
import com.example.skillcheckerapp.ui.theme.DashboardCard

@Composable
fun GithubStatsCard(
    repositories: List<Repository>,
    modifier: Modifier = Modifier
) {
    val repositoryCount =
        repositories.size

    val totalStars =
        repositories.sumOf { repository ->
            repository.stargazers_count ?: 0
        }

    val totalForks =
        repositories.sumOf { repository ->
            repository.forks_count ?: 0
        }

    val languageCount =
        repositories
            .mapNotNull { repository ->
                repository.language
            }
            .filter { language ->
                language.isNotBlank()
            }
            .distinct()
            .size

    Card(
        modifier =
            modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(16.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    DashboardCard
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
    ) {
        Column(
            modifier =
                Modifier.padding(24.dp),
            verticalArrangement =
                Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "GitHub Stats",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight =
                    FontWeight.SemiBold
            )

            Column(
                verticalArrangement =
                    Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(16.dp)
                ) {
                    GithubStatItem(
                        label = "Repository",
                        value = repositoryCount,
                        modifier =
                            Modifier.weight(1f)
                    )

                    GithubStatItem(
                        label = "Stars",
                        value = totalStars,
                        modifier =
                            Modifier.weight(1f)
                    )
                }

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(16.dp)
                ) {
                    GithubStatItem(
                        label = "Forks",
                        value = totalForks,
                        modifier =
                            Modifier.weight(1f)
                    )

                    GithubStatItem(
                        label = "Languages",
                        value = languageCount,
                        modifier =
                            Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun GithubStatItem(
    label: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .heightIn(
                min = 100.dp
            ),
        shape =
            RoundedCornerShape(12.dp),
        color =
            DashboardBackground,
        border =
            BorderStroke(
                width = 1.dp,
                color = DashboardBorder
            )
    ) {
        Column(
            modifier =
                Modifier.padding(16.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally,
            verticalArrangement =
                Arrangement.Center
        ) {
            Surface(
                shape =
                    RoundedCornerShape(
                        percent = 50
                    ),
                color =
                    DashboardAccent.copy(
                        alpha = 0.15f
                    ),
                border =
                    BorderStroke(
                        width = 1.dp,
                        color =
                            DashboardAccent.copy(
                                alpha = 0.30f
                            )
                    )
            ) {
                Text(
                    text = label.uppercase(),
                    modifier =
                        Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                    color =
                        DashboardAccent,
                    fontSize = 11.sp,
                    fontWeight =
                        FontWeight.SemiBold,
                    maxLines = 1
                )
            }

            Text(
                text = value.toString(),
                modifier =
                    Modifier.padding(
                        top = 12.dp
                    ),
                color = Color.White,
                fontSize = 32.sp,
                fontWeight =
                    FontWeight.Bold,
                lineHeight = 32.sp
            )
        }
    }
}