package com.neo.moneytracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun DonutChartWithTotal(
    modifier: Modifier = Modifier,
    data: List<Pair<String, Int>>,
    colors: List<Color>,
    strokeWidth: Float = 40f,
    textStyle: TextStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
) {
    var total = 0f
    for (i in data){
        total += i.second
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val proportions = data.map { it.second / total }
            val sweepAngles = proportions.map { it * 360f }
            var startAngle = -90f

            sweepAngles.forEachIndexed { index, sweep ->
                drawArc(
                    color = colors.getOrElse(index) { Color.Gray },
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round,
                    )
                )
                startAngle += sweep
            }
        }

        Text(
            text = total.toInt().toString(),
            style = textStyle
        )
    }
}
