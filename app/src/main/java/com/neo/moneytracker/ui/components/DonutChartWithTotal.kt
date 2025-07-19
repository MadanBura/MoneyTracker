package com.neo.moneytracker.ui.components

import android.util.Log
import android.view.ViewGroup
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.neo.moneytracker.utils.listOfColors

@Composable
fun DonutChartWithTotal(
    modifier: Modifier = Modifier,
    data1: List<Pair<String, Int>>
) {
    Log.d("HELLO_DATA_CHART", data1.toString())
    val entries = data1.map { PieEntry(it.second.toFloat(), it.first) }
    val colors = listOfColors().map { it.toArgb() }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 1.dp), // space from top
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp), // 👈 Increased graph height
            factory = { context ->
                PieChart(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    // Pie chart appearance
                    isDrawHoleEnabled = true
                    setHoleColor(android.graphics.Color.WHITE)
                    setDrawEntryLabels(false)
                    centerText = "Expenses"
                    setCenterTextSize(12f)
                    legend.isEnabled = false
                    description.isEnabled = false

                    val dataSet = PieDataSet(entries, "Expense Categories").apply {
                        this.colors = colors
                        valueTextSize = 10f
                        valueTextColor = android.graphics.Color.BLACK
                        setDrawValues(false)
                    }

                    data = PieData(dataSet)
                    animateY(1000)
                }
            }
        )

    }
}
