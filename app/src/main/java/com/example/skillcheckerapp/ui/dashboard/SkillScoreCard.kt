package com.example.skillcheckerapp.ui.dashboard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.skillcheckerapp.ui.theme.DashboardCard
import com.example.skillcheckerapp.ui.theme.DashboardSubText

@Composable
fun SkillScoreCard(
    repositories: List<Repository>,
    modifier: Modifier = Modifier
) {
    val averageScore =
        if (repositories.isNotEmpty()) {
            repositories
                .map { repository ->
                    repository.score ?: 0
                }
                .average()
                .toInt()
        } else {
            0
        }

    val progress =
        (averageScore / 100f)
            .coerceIn(
                minimumValue = 0f,
                maximumValue = 1f
            )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = DashboardCard
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
                text = "Skill Score",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )

            Box(
                modifier =
                    Modifier
                        .size(160.dp)
                        .border(
                            width = 12.dp,
                            color = DashboardAccent,
                            shape = CircleShape
                        )
                        .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = averageScore.toString(),
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "総合偏差値",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )

                LinearProgressIndicator(
                    progress = {
                        progress
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    color = DashboardAccent,
                    trackColor = Color(0xFF1F2937),
                    gapSize = 0.dp,
                    drawStopIndicator = {}
                )

                androidx.compose.foundation.layout.Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "0",
                        color = DashboardSubText,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "50",
                        color = DashboardSubText,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "100",
                        color = DashboardSubText,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}