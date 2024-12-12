package com.zhiro.jetstep.feature.dashboard.presentation.dashboard.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhiro.jetstep.core.ui.utils.color
import com.zhiro.jetstep.core.ui.utils.text
import com.zhiro.jetstep.domain.model.StepBatch
import com.zhiro.jetstep.domain.model.StepType
import com.zhiro.jetstep.domain.model.aggregate

@Composable
@Preview
internal fun PieChart(
    modifier: Modifier = Modifier,
    data: List<StepBatch> = listOf(
        StepBatch(
            steps = 500,
            startTime = 0, // Start from now
            endTime = 0, // 10 minutes later
            type = StepType.Walking
        ),
        StepBatch(
            steps = 750,
            startTime = 0, // Start from now
            endTime = 0, // 10 minutes later
            type = StepType.Running
        ),
        StepBatch(
            steps = 200,
            startTime = 0, // Start from now
            endTime = 0, // 10 minutes later
            type = StepType.Jogging
        ),
        StepBatch(
            steps = 1000,
            startTime = 0, // Start from now
            endTime = 0, // 10 minutes later
            type = StepType.Walking
        ),
        StepBatch(
            steps = 300,
            startTime = 0, // Start from now
            endTime = 0, // 10 minutes later
            type = StepType.Walking
        )
    ),
    radiusOuter: Dp = 140.dp,
    chartBarWidth: Dp = 35.dp,
    animDuration: Int = 1000,
) {
    val totalSum = data.sumOf(StepBatch::steps)
    val floatValue = mutableListOf<Float>()
    val aggregatedData by remember(data) {
        mutableStateOf(data.aggregate())
    }
    val stepsCount by remember(aggregatedData) {
        mutableIntStateOf(aggregatedData.values.sum())
    }
    // To set the value of each Arc according to
    // the value given in the data, we have used a simple formula.
    // For a detailed explanation check out the Medium Article.
    // The link is in the about section and readme file of this GitHub Repository
    aggregatedData.values.forEachIndexed { index, steps ->
        floatValue.add(index, 360 * steps.toFloat() / totalSum.toFloat())
    }
    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    // it is the diameter value of the Pie
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )
    val animateStepsCount by animateIntAsState(
        targetValue = if (animationPlayed) stepsCount else 0,
        animationSpec = tween(
            durationMillis = animDuration, delayMillis = 0, easing = LinearOutSlowInEasing
        )
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration, delayMillis = 0, easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .offset { IntOffset.Zero }
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                // draw each Arc for each data entry in Pie Chart
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = aggregatedData.keys.elementAt(index).color,
                        startAngle = lastValue,
                        sweepAngle = value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = animateStepsCount.toString(),
                fontWeight = FontWeight.Medium,
                fontSize = 32.sp,
                color = Color.Black
            )
        }

        DetailsPieChart(
            aggregatedData = aggregatedData
        )
    }
}

@Composable
fun DetailsPieChart(aggregatedData: Map<StepType, Int>) {
    Column(
        modifier = Modifier
            .padding(top = 80.dp)
            .fillMaxWidth()
    ) {
        aggregatedData.values.forEachIndexed { index, value ->
            DetailsPieChartItem(
                data = Pair(aggregatedData.keys.elementAt(index).text, value),
                color = aggregatedData.keys.elementAt(index).color
            )
        }

    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Int>,
    height: Dp = 45.dp,
    color: Color
) {

    Surface(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 40.dp),
        color = Color.Transparent
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = color,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(height)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.first,
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = Color.Black
                )

                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.second.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = Color.Gray
                )
            }
        }

    }

}