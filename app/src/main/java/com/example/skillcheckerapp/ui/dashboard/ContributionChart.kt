package com.example.skillcheckerapp.ui.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillcheckerapp.ui.theme.DashboardAccent
import com.example.skillcheckerapp.ui.theme.DashboardBorder
import com.example.skillcheckerapp.ui.theme.DashboardCard
import com.example.skillcheckerapp.ui.theme.DashboardSubText
import java.time.YearMonth

private data class MonthlyContribution(
    val month: String,
    val commits: Int
)

@Composable
fun ContributionChart(
    contributions: Map<String, Int>,
    modifier: Modifier = Modifier
) {
    val chartData =
        remember(contributions) {
            createChartData(
                contributions = contributions
            )
        }

    val totalCommits =
        chartData.sumOf { item ->
            item.commits
        }

    val activeMonths =
        chartData.count { item ->
            item.commits > 0
        }

    val averageCommits =
        if (activeMonths > 0) {
            totalCommits / activeMonths
        } else {
            0
        }

    val maxCommits =
        chartData.maxOfOrNull { item ->
            item.commits
        } ?: 0

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
                text = "Contribution",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight =
                    FontWeight.SemiBold
            )

            ContributionLineChart(
                chartData = chartData
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
                    ContributionSummaryItem(
                        label = "総コミット数",
                        value = totalCommits,
                        modifier =
                            Modifier.weight(1f)
                    )

                    ContributionSummaryItem(
                        label = "アクティブ月数",
                        value = activeMonths,
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
                    ContributionSummaryItem(
                        label = "月平均コミット",
                        value = averageCommits,
                        modifier =
                            Modifier.weight(1f)
                    )

                    ContributionSummaryItem(
                        label = "最大コミット数",
                        value = maxCommits,
                        modifier =
                            Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ContributionLineChart(
    chartData: List<MonthlyContribution>
) {
    val scrollState =
        rememberScrollState()

    val maxCommits =
        chartData.maxOfOrNull { item ->
            item.commits
        }
            ?.coerceAtLeast(1)
            ?: 1

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    scrollState
                )
    ) {
        Canvas(
            modifier =
                Modifier
                    .requiredWidth(560.dp)
                    .height(220.dp)
        ) {
            val horizontalPadding =
                32.dp.toPx()

            val verticalPadding =
                20.dp.toPx()

            val chartWidth =
                size.width -
                        horizontalPadding * 2

            val chartHeight =
                size.height -
                        verticalPadding * 2

            val gridColor =
                DashboardBorder.copy(
                    alpha = 0.8f
                )

            for (index in 0..4) {
                val y =
                    verticalPadding +
                            chartHeight *
                            index / 4f

                drawLine(
                    color = gridColor,
                    start =
                        Offset(
                            x = horizontalPadding,
                            y = y
                        ),
                    end =
                        Offset(
                            x = size.width -
                                    horizontalPadding,
                            y = y
                        ),
                    strokeWidth = 1.dp.toPx()
                )
            }

            val points =
                chartData.mapIndexed {
                        index,
                        item ->

                    val x =
                        if (
                            chartData.size > 1
                        ) {
                            horizontalPadding +
                                    chartWidth *
                                    index /
                                    (
                                            chartData.size -
                                                    1
                                            )
                        } else {
                            horizontalPadding
                        }

                    val yRatio =
                        item.commits.toFloat() /
                                maxCommits.toFloat()

                    val y =
                        verticalPadding +
                                chartHeight *
                                (
                                        1f -
                                                yRatio
                                        )

                    Offset(
                        x = x,
                        y = y
                    )
                }

            if (points.isNotEmpty()) {
                val path =
                    Path().apply {
                        moveTo(
                            points.first().x,
                            points.first().y
                        )

                        points
                            .drop(1)
                            .forEach { point ->
                                lineTo(
                                    point.x,
                                    point.y
                                )
                            }
                    }

                drawPath(
                    path = path,
                    color =
                        DashboardAccent,
                    style =
                        Stroke(
                            width =
                                3.dp.toPx(),
                            cap =
                                StrokeCap.Round
                        )
                )

                points.forEach { point ->
                    drawCircle(
                        color =
                            DashboardAccent,
                        radius =
                            5.dp.toPx(),
                        center = point
                    )

                    drawCircle(
                        color =
                            DashboardCard,
                        radius =
                            2.dp.toPx(),
                        center = point
                    )
                }
            }
        }

        Row(
            modifier =
                Modifier
                    .requiredWidth(560.dp)
                    .padding(
                        horizontal = 16.dp
                    ),
            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {
            chartData.forEach { item ->
                Text(
                    text = item.month,
                    color =
                        DashboardSubText,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun ContributionSummaryItem(
    label: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape =
            RoundedCornerShape(12.dp),
        color =
            Color.Transparent
    ) {
        Column(
            modifier =
                Modifier.padding(
                    vertical = 8.dp
                ),
            horizontalAlignment =
                Alignment.Start
        ) {
            Text(
                text = label,
                color =
                    DashboardSubText,
                fontSize = 12.sp
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Text(
                text = value.toString(),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight =
                    FontWeight.Bold,
                lineHeight = 28.sp
            )
        }
    }
}

private fun createChartData(
    contributions: Map<String, Int>
): List<MonthlyContribution> {
    val monthlyData =
        contributions.entries
            .groupBy { entry ->
                entry.key.take(7)
            }
            .mapValues { (_, entries) ->
                entries.sumOf { entry ->
                    entry.value
                }
            }

    val currentMonth =
        YearMonth.now()

    return (5 downTo 0).map { monthsAgo ->
        val targetMonth =
            currentMonth.minusMonths(
                monthsAgo.toLong()
            )

        val key =
            targetMonth.toString()

        MonthlyContribution(
            month =
                "${
                    targetMonth.monthValue
                        .toString()
                        .padStart(2, '0')
                }月",
            commits =
                monthlyData[key] ?: 0
        )
    }
}