package com.neo.moneytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.neo.moneytracker.ui.theme.IconBackGroundColor
import com.neo.moneytracker.ui.theme.LemonSecondary
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CustomCalendarDialog(
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    isCompact: Boolean = false
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = if (isCompact) {
                modifier
                    .width(320.dp)
                    .wrapContentHeight()
            } else {
                modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            },
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp,
            color = Color.White
        ) {
            CalendarContent(
                onDismiss = onDismiss,
                onDateSelected = onDateSelected
            )
        }
    }
}

@Composable
private fun CalendarContent(
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    val today = remember { Calendar.getInstance() }
    var displayedMonth by remember { mutableStateOf(today.get(Calendar.MONTH)) }
    var displayedYear by remember { mutableStateOf(today.get(Calendar.YEAR)) }
    var selectedDay by remember { mutableStateOf(-1) }

    val daysInMonth = remember(displayedMonth, displayedYear) {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, displayedYear)
            set(Calendar.MONTH, displayedMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    val firstDayOfWeek = remember(displayedMonth, displayedYear) {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, displayedYear)
            set(Calendar.MONTH, displayedMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }.get(Calendar.DAY_OF_WEEK) - 1
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                if (displayedMonth == 0) {
                    displayedMonth = 11
                    displayedYear--
                } else {
                    displayedMonth--
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }

            Text(
                text = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(
                    Calendar.getInstance().apply {
                        set(Calendar.YEAR, displayedYear)
                        set(Calendar.MONTH, displayedMonth)
                    }.time
                ),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                fontSize = 16.sp
            )

            IconButton(onClick = {
                if (displayedMonth == 11) {
                    displayedMonth = 0
                    displayedYear++
                } else {
                    displayedMonth++
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Weekdays
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach {
                Text(text = it, fontSize = 12.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Calendar grid
        val totalCells = daysInMonth + firstDayOfWeek
        val rows = (totalCells / 7) + if (totalCells % 7 > 0) 1 else 0

        Column {
            for (row in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (col in 0..6) {
                        val day = row * 7 + col - firstDayOfWeek + 1
                        if (day in 1..daysInMonth) {
                            val currentDate = Calendar.getInstance().apply {
                                set(displayedYear, displayedMonth, day, 0, 0, 0)
                                set(Calendar.MILLISECOND, 0)
                            }

                            val isFutureDate = currentDate.after(today)
                            val isSelected = selectedDay == day

                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isSelected -> LemonSecondary
                                            isFutureDate -> Color.LightGray
                                            else -> IconBackGroundColor
                                        }
                                    )
                                    .clickable(enabled = !isFutureDate) {
                                        selectedDay = day
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = day.toString(),
                                    color = if (isFutureDate) Color.Gray else Color.Black,
                                    fontSize = 14.sp
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.size(36.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel", color = LemonSecondary, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }

            Button(
                onClick = {
                    if (selectedDay != -1) {
                        val selectedDate = Calendar.getInstance().apply {
                            set(displayedYear, displayedMonth, selectedDay)
                        }
                        val formatted = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                            selectedDate.time
                        )
                        onDateSelected(formatted)
                        onDismiss()
                    }
                },
                enabled = selectedDay != -1,
                colors = ButtonDefaults.buttonColors(containerColor = LemonSecondary)
            ) {
                Text("Confirm")
            }
        }
    }
}
