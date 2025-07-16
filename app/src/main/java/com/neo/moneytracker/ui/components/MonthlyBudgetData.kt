package com.neo.moneytracker.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MonthlyBudgetData(
    text: String,
    no: Int,
    modifier: Modifier = Modifier
) {
    Row {
        Text(
            text = text,
            modifier = modifier.weight(1f)
        )
        Text(
            text = "$no",
            modifier = modifier
        )
    }
}
