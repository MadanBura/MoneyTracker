package com.neo.moneytracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun MonthPickerButton(onMonthSelected: (String) -> Unit = {}) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        MonthYearPickerDialog(
            onDismiss = { showDialog = false },
            onDateSelected = {
                onMonthSelected(it)
            }
        )
    }

    IconButton(onClick = { showDialog = true }) {
        Icon(
            imageVector = Icons.Default.CalendarMonth,
            contentDescription = "Pick Month",
            tint = Color(0xFFFFC107)
        )
    }
}
