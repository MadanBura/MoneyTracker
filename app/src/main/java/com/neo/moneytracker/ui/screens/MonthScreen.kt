package com.neo.moneytracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.data.mapper.toDataEntity
import com.neo.moneytracker.domain.model.Transaction
import com.neo.moneytracker.ui.components.DonutChartPreview
import com.neo.moneytracker.ui.components.ScrollableRow
import com.neo.moneytracker.ui.viewmodel.TransactionViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthScreen(
    transaction: List<TransactionEntity>,
) {
    val months: List<Pair<String, YearMonth>> = remember {
        val now = YearMonth.now()
        (0 until 30).map { index ->
            val label = when (index) {
                0 -> "This Month"
                1 -> "Last Month"
                else -> now.minusMonths(index.toLong())
                    .format(DateTimeFormatter.ofPattern("MMM yyyy"))
            }
            label to now.minusMonths(index.toLong())
        }.reversed()
    }

    // Initially select "Last Month"
    var selectedIndex by remember {
        mutableStateOf(months.indexOfFirst { it.first == "This Month" })
    }

    val selectedMonth = months[selectedIndex].second

    // UI: Scrollable row
    ScrollableRow(
        items = months.map { it.first },
        selectedIndex = selectedIndex,
        onItemClick = { selectedIndex = it }
    )

    // Filter logic
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val filteredTransactions = transaction.filter {
        try {
            val date = LocalDate.parse(it.date, dateFormatter)
            YearMonth.from(date) == selectedMonth
        } catch (e: Exception) {
            false
        }
    }

    if (filteredTransactions.isNotEmpty()) {
        DonutChartPreview(filteredTransactions)
    } else {
        Text(
            text = "No Records Found",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.h6,
            color = Color.Gray
        )
    }
}

