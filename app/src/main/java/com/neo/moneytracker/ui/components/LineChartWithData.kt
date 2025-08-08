package com.neo.moneytracker.ui.components

import android.graphics.Color
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.neo.moneytracker.utils.listOfColors
import java.text.DecimalFormat

@Composable
fun LineChartWithData(
    modifier: Modifier = Modifier,
    monthlyData: Map<Int, Float>, // mapOf(day -> value)
    monthName: String // e.g., "July"
) {
    val total = monthlyData.values.sum()
    val avg = if (monthlyData.isNotEmpty()) total / monthlyData.size else 0f
    val formattedAvg = DecimalFormat("#.##").format(avg)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Total: ${total.toInt()}", fontSize = 16.sp)
        Text(text = "Average: $formattedAvg", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            factory = { context ->
                LineChart(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    val entries = mutableListOf<Entry>()
                    for (i in 1..31) {
                        val value = monthlyData[i] ?: 0f
                        entries.add(Entry(i.toFloat(), value))
                    }

                    val dataSet = LineDataSet(entries, "Daily Data").apply {
                        color = Color.parseColor("#00BFA5")
                        valueTextSize = 10f
                        circleRadius = 4f
                        setCircleColor(Color.parseColor("#00BFA5"))
                        lineWidth = 2f
                        setDrawValues(false)
                        setDrawFilled(false)
                    }

                    this.data = LineData(dataSet)

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        granularity = 1f
                        setDrawGridLines(false)
                        textSize = 12f
                        labelRotationAngle = -45f
                        valueFormatter = XAxisValueFormatter(monthName)
                    }

                    axisRight.isEnabled = false
                    axisLeft.textSize = 12f
                    legend.isEnabled = false
                    description = Description().apply { text = "" }

                    animateX(1000)
                }
            }
        )
    }
}


class XAxisValueFormatter(private val month: String) : com.github.mikephil.charting.formatter.ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return if (value % 7 == 0f || value == 1f) {
            "$month ${value.toInt()}"
        } else {
            ""
        }
    }
}