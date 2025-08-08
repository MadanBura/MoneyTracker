package com.neo.moneytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.moneytracker.ui.theme.IconBackGroundColor
import com.neo.moneytracker.ui.theme.LemonSecondary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun CustomCalenderRecordsScreen(
    onDateSelected: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    val today = remember { Calendar.getInstance() }
    var displayedMonth by remember { mutableStateOf(today.get(Calendar.MONTH)) }
    var displayedYear by remember { mutableStateOf(today.get(Calendar.YEAR)) }
    var selectedDay by remember { mutableStateOf(-1) }

    val daysInMonth = remember(displayedMonth, displayedYear) {
        val cal = Calendar.getInstance().apply {
            set(Calendar.YEAR, displayedYear)
            set(Calendar.MONTH, displayedMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    val firstDayOfWeek = remember(displayedMonth, displayedYear) {
        val cal = Calendar.getInstance().apply {
            set(Calendar.YEAR, displayedYear)
            set(Calendar.MONTH, displayedMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        cal.get(Calendar.DAY_OF_WEEK) - 1
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        // Header with Back and Month Navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }

            Text(
                text = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(
                    Calendar.getInstance().apply {
                        set(Calendar.YEAR, displayedYear)
                        set(Calendar.MONTH, displayedMonth)
                    }.time
                ),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            IconButton(onClick = {
                if (displayedMonth == 11) {
                    displayedMonth = 0
                    displayedYear++
                } else {
                    displayedMonth++
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Month")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach {
                Text(text = it, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Calendar Grid
        val totalCells = daysInMonth + firstDayOfWeek
        val rows = (totalCells / 7) + if (totalCells % 7 > 0) 1 else 0

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

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
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
                    }
                },
                enabled = selectedDay != -1,
                colors = ButtonDefaults.buttonColors(containerColor = LemonSecondary)
            ) {
                Text("Select")
            }
        }
    }
}
