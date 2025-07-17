package com.neo.moneytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun MonthYearPickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = (1990..currentYear + 10).toList()

    var selectedMonthIndex by remember {
        mutableStateOf(
            Calendar.getInstance().get(Calendar.MONTH)
        )
    }
    var selectedYearIndex by remember { mutableStateOf(years.indexOf(currentYear)) }
    var showMonthGrid by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                val month = months[selectedMonthIndex]
                val year = years[selectedYearIndex]
                onDateSelected("$month $year")
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        },
        title = {
            Text("${months[selectedMonthIndex]} ${years[selectedYearIndex]}")
        },
        text = {
            Column {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { showMonthGrid = !showMonthGrid }
                    ) {

                            Text(
                                text = years[selectedYearIndex].toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Icon(
                                imageVector = if (showMonthGrid) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp,
                                contentDescription = null
                            )
                        }
                    Spacer(modifier = Modifier.weight(1f))
                    Row {
                        IconButton(onClick = {
                            if (selectedYearIndex > 0) selectedYearIndex--
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Previous Year"
                            )
                        }

                        IconButton(onClick = {
                            if (selectedYearIndex < years.lastIndex) selectedYearIndex++
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowForwardIos,
                                contentDescription = "Next Year"
                            )
                        }
                    }


                }

                // Grid Layout
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (showMonthGrid) {
                        itemsIndexed(months) { index, month ->
                            val isSelected = selectedMonthIndex == index

                            TextButton(
                                onClick = { selectedMonthIndex = index },
                                modifier = Modifier
                                    .width(110.dp)
                                    .height(48.dp)
                                    .background(
                                        color = if (isSelected) Color(0xFFFFEB3B) else Color.Transparent,
                                        shape = RoundedCornerShape(32.dp) // Optional: rounded background
                                    )
                            ) {
                                Text(
                                    text = month,
                                    softWrap = false,
                                    maxLines = 1,
                                    color = if (isSelected)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }


                    } else {
                        itemsIndexed(years) { index, year ->
                            TextButton(
                                onClick = { selectedYearIndex = index },
                                modifier = Modifier
                                    .width(110.dp)
                                    .height(48.dp)
                            ) {
                                Text(
                                    text = year.toString(),
                                    softWrap = false,
                                    maxLines = 1,
                                    color = if (selectedYearIndex == index)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

            }
        }
    )
}
