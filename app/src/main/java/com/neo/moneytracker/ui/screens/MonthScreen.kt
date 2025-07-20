package com.neo.moneytracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.data.mapper.toDataEntity
import com.neo.moneytracker.domain.model.Transaction
import com.neo.moneytracker.ui.components.DonutChartPreview
import com.neo.moneytracker.ui.components.ScrollableRow
import com.neo.moneytracker.ui.viewmodel.TransactionViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthScreen(
   transaction : List<TransactionEntity>,
    modifier: Modifier = Modifier
) {
    val allItems = remember { generateMonthLabels(30) }
    ScrollableRow(allItems)

//    val data = transactionViewModel.transactions.collectAsState().value

    if(transaction.isNotEmpty()){
        DonutChartPreview(transaction)
    }
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

