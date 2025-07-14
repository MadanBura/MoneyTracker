package com.neo.moneytracker.ui.components

import android.app.DatePickerDialog
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.util.*


@Composable
fun CalendarPickerButton(
    onDateSelected: (String) -> Unit = {}
) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDateSelected(selectedDate)
                showDatePicker = false
            },
            year, month, day
        ).show()
    }

    IconButton(onClick = { showDatePicker = true }) {
        Icon(
            imageVector = Icons.Default.CalendarMonth,
            contentDescription = "Pick a date"
        )
    }
}
