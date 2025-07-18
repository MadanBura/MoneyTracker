package com.neo.moneytracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neo.moneytracker.ui.components.MonthlyBudgetCard
import com.neo.moneytracker.ui.components.MonthlyStatsCard

@Composable
fun AnalyticsScreen(
    expenseAmount: Double,
    incomeAmount: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        MonthlyStatsCard(expenseAmount, incomeAmount)
        Spacer(modifier = Modifier.height(12.dp))
        MonthlyBudgetCard()
    }
}
