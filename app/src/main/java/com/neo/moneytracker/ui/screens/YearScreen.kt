package com.neo.moneytracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.neo.moneytracker.ui.components.DonutChartPreview
import com.neo.moneytracker.ui.components.ScrollableRow
import com.neo.moneytracker.ui.viewmodel.TransactionViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YearScreen(
    transactionViewModel: TransactionViewModel,
    modifier: Modifier = Modifier
) {
    val allItems = remember { generateYearLabels(30) }
    ScrollableRow(allItems)
    val data = transactionViewModel.transactions.collectAsState().value

    DonutChartPreview(data)
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateYearLabels(count: Int): List<String> {
    val now = LocalDate.now()
    return List(count) { index ->
        when (index) {
            0 -> "This Year"
            1 -> "Last Year"
            else -> now.minusYears(index.toLong()).format(DateTimeFormatter.ofPattern("yyyy"))
        }
    }
}