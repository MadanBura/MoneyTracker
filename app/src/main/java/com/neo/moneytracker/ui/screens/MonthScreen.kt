package com.neo.moneytracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.neo.moneytracker.ui.components.DonutChartPreview
import com.neo.moneytracker.ui.components.DonutChartWithTotal
import com.neo.moneytracker.ui.components.ScrollableRow
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.theme.LightBlue
import com.neo.moneytracker.utils.listOfColors
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthScreen(modifier: Modifier = Modifier) {
    val allItems = remember { generateMonthLabels(30) }
    ScrollableRow(allItems)

    val data = listOf(
        "Food" to 500,
        "Shopping" to 50
    )
    DonutChartPreview(data)
}


@RequiresApi(Build.VERSION_CODES.O)
fun generateMonthLabels(count: Int): List<String> {
    val now = LocalDate.now()
    return List(count) { index ->
        when (index) {
            0 -> "This Month"
            1 -> "Last Month"
            else -> now.minusMonths(index.toLong()).format(DateTimeFormatter.ofPattern("MMM yyyy"))
        }
    }
}

