package com.neo.moneytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.neo.moneytracker.ui.theme.LemonSecondary
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StickyFirstWithLazyRow(expenseAmount: Int, incomeAmount: Int) {
    val categories = listOf("Expenses", "Income", "Balance")
    var showMonthPicker by remember { mutableStateOf(false) }

    if (showMonthPicker) {
        MonthYearPickerDialog(
            onDismiss = { showMonthPicker = false },
            onDateSelected = {
                println("Selected Month: $it")
                showMonthPicker = false
            }
        )
    }

    Row(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .background(LemonSecondary)
                .padding(16.dp)
                .clickable {
                    showMonthPicker = true
                }
        ) {
            Column {
                Text("2025")
                Row {
                    Text(
                        text = "JUL",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Menu Icon"
                    )
                }
            }
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(categories) { category ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(LemonSecondary)
                        .padding(0.dp)
                ) {
                    Column {
                        Text(category)
                        Text(
                            text = when (category) {
                                "Expenses" -> "${expenseAmount}"
                                "Income" -> "${incomeAmount}"
                                "Balance" -> "${incomeAmount - expenseAmount}"
                                else -> "0"
                            } ,
                            modifier = when (category) {
                                "Expenses" -> Modifier.testTag("ExpensesField")
                                "Income" -> Modifier.testTag("IncomeField")
                                "Balance" -> Modifier.testTag("BalanceField")
                                else -> Modifier
                            }
                        )
                    }
                }
            }
        }
    }
}

