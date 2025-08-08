package com.neo.moneytracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.neo.moneytracker.ui.viewmodel.TransactionViewModel

@Composable
fun DailySummaryRow(date: String, viewModel: TransactionViewModel) {
    val totalsMap by viewModel.dateWiseTotals.collectAsState()
    val totals = totalsMap[date] ?: 0 to 0

    val income = totals.first
    val expense = totals.second

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = date, fontWeight = FontWeight.Bold)
        Text(text = "Income: ₹$income / Expense: ₹$expense")
    }
}
