package com.neo.moneytracker.ui.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.neo.moneytracker.R
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.ui.navigation.SealedBottomNavItem
import com.neo.moneytracker.ui.theme.IconBackGroundColor
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.viewmodel.TransactionViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AmountInputDialog(
    iconRes: Int,
    selectedCategory: String,
    transactionViewModel: TransactionViewModel,
    navController: NavController
) {
    var note by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("0") }
    var showCalendar by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    var selectedDate by remember {
        mutableStateOf(SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(calendar.time))
    }

    val keypadButtons = listOf(
        listOf("7", "8", "9", "Date"),
        listOf("4", "5", "6", "+"),
        listOf("1", "2", "3", "-"),
        listOf(".", "0", "⌫", "✔")
    )

    Box(
        modifier = Modifier
            .background(IconBackGroundColor)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.checklist),
                    contentDescription = "Settings Icon",
                    modifier = Modifier.size(30.dp)
                )

                Text(
                    text = amount,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(end = 4.dp),
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Note :",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(end = 8.dp)
                )

                TextField(
                    value = note,
                    onValueChange = { note = it },
                    placeholder = { Text("Enter a note", color = Color.Gray) },
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = { /* handle camera click */ }) {
                    Image(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = "Camera Icon",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            keypadButtons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    row.forEach { label ->
                        Button(
                            onClick = {
                                when (label) {
                                    "⌫" -> amount = if (amount.length > 1) amount.dropLast(1) else "0"
                                    "✔" -> {
                                        val transaction = TransactionEntity(
                                            iconRes = iconRes,
                                            amount = amount,
                                            note = note,
                                            date = selectedDate,
                                            category = selectedCategory
                                        )
                                        transactionViewModel.addTransaction(transaction)
                                        amount = "0"
                                        note = ""
                                        navController.navigate(SealedBottomNavItem.records.route) {
                                            popUpTo(SealedBottomNavItem.records.route) { inclusive = false }
                                        }
                                    }
                                    "Date" -> showCalendar = true
                                    "+", "-" -> { /* handle operations if needed */ }
                                    "." -> if (!amount.contains(".")) amount += "."
                                    else -> amount = if (amount == "0") label else amount + label
                                }
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .weight(1f)
                                .padding(horizontal = 1.dp, vertical = 3.dp),
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (label == "✔") LemonSecondary else Color.White
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            when (label) {
                                "Date" -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.calendar),
                                        contentDescription = "Calendar Icon",
                                        tint = Color(0xFFFFC107),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = selectedDate,
                                        fontSize = 12.sp,
                                        color = Color(0xFFFFC107),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                "✔" -> Icon(
                                    painter = painterResource(id = R.drawable.check_),
                                    contentDescription = "Check Icon",
                                    tint = Color.Black,
                                    modifier = Modifier.size(20.dp)
                                )
                                else -> Text(
                                    text = label,
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCalendar) {
        Dialog(onDismissRequest = { showCalendar = false }) {
            CustomCalendarDialog(
                onDismiss = { showCalendar = false },
                onDateSelected = { selected ->
                    selectedDate = selected
                }
            )
        }
    }
}

@Composable
fun CustomCalendarDialog(
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }

                Text(
                    text = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(
                        Calendar.getInstance().apply {
                            set(Calendar.YEAR, displayedYear)
                            set(Calendar.MONTH, displayedMonth)
                        }.time
                    ),
                    fontWeight = FontWeight.Bold,
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
                    Icon(Icons.Default.ArrowForward, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Weekdays
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                listOf("S", "M", "T", "W", "T", "F", "S").forEach {
                    Text(text = it, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
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
                    Text(text = "Cancel", color = LemonSecondary, fontWeight = FontWeight.Bold)
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
}




//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.window.Dialog
//import java.time.LocalDate
//import java.time.format.TextStyle
//import java.util.*
//
//
//
//
//import android.widget.Toast
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
////import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun CustomCalendarDialog(
//    selectedDate: LocalDate,
//    onDismiss: () -> Unit,
//    onConfirm: (LocalDate) -> Unit
//) {
//    var currentMonth by remember { mutableStateOf(selectedDate.withDayOfMonth(1)) }
//    var tempSelectedDate by remember { mutableStateOf(selectedDate) }
//
//    Surface(
//        shape = MaterialTheme.shapes.medium,
//        color = Color.White,
//        tonalElevation = 8.dp
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//                .width(IntrinsicSize.Min),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "${tempSelectedDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())}, " +
//                        "${tempSelectedDate.dayOfMonth} " +
//                        "${tempSelectedDate.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())}, " +
//                        "${tempSelectedDate.year}",
//                style = MaterialTheme.typography.titleMedium,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                IconButton(onClick = {
//                    currentMonth = currentMonth.minusMonths(1)
//                }) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month")
//                }
//
//                Text(
//                    text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
//                    style = MaterialTheme.typography.titleSmall
//                )
//
//                IconButton(onClick = {
//                    currentMonth = currentMonth.plusMonths(1)
//                }) {
//                    Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
//                }
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Weekdays Header
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
//                    Text(
//                        text = day,
//                        modifier = Modifier.weight(1f),
//                        style = MaterialTheme.typography.labelSmall,
//                        color = Color.Gray,
//                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
//                    )
//                }
//            }
//
//            val daysInMonth = currentMonth.lengthOfMonth()
//            val firstDayOfWeek = currentMonth.dayOfWeek.value % 7
//
//            val dates = buildList {
//                repeat(firstDayOfWeek) { add(null) }
//                for (day in 1..daysInMonth) {
//                    add(currentMonth.withDayOfMonth(day))
//                }
//            }
//
//            Column {
//                dates.chunked(7).forEach { week ->
//                    Row(modifier = Modifier.fillMaxWidth()) {
//                        week.forEach { date ->
//                            Box(
//                                modifier = Modifier
//                                    .weight(1f)
//                                    .aspectRatio(1f)
//                                    .padding(4.dp)
//                                    .clickable(enabled = date != null) {
//                                        date?.let { tempSelectedDate = it }
//                                    }
//                                    .background(
//                                        color = if (date == tempSelectedDate) Color(0xFFFFD54F) else Color.Transparent,
//                                        shape = MaterialTheme.shapes.small
//                                    ),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Text(
//                                    text = date?.dayOfMonth?.toString() ?: "",
//                                    color = if (date == tempSelectedDate) Color.Black else Color.DarkGray
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                TextButton(onClick = onDismiss) {
//                    Text("Cancel", color = Color(0xFFFFD54F))
//                }
//                TextButton(onClick = {
//                    onConfirm(tempSelectedDate)
//                }) {
//                    Text("Confirm", color = Color(0xFFFFD54F))
//                }
//            }
//        }
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun AmountInputDialog(
//    onDismiss: () -> Unit,
//    onAddTransaction: (TransactionEntity) -> Unit,
//    iconRes: Int,
//    selectedCategory: String
//) {
//    val context = LocalContext.current
//
//    var amount by remember { mutableStateOf("") }
//    var note by remember { mutableStateOf("") }
//
//    var selectedDateObj by remember { mutableStateOf(LocalDate.now()) }
//    val selectedDateFormatted = selectedDateObj.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
//    var showCalendar by remember { mutableStateOf(false) }
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        confirmButton = {
//            TextButton(onClick = {
//                if (amount.isNotBlank() && note.isNotBlank()) {
//                    val transaction = TransactionEntity(
//                        iconRes = iconRes,
//                        amount = amount,
//                        note = note,
//                        date = selectedDateFormatted,
//                        category = selectedCategory
//                    )
//                    onAddTransaction(transaction)
//                    onDismiss()
//                } else {
//                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
//                }
//            }) {
//                Text("✔", style = MaterialTheme.typography.titleLarge)
//            }
//        },
//        title = { Text("Enter Amount") },
//        text = {
//            Column(modifier = Modifier.fillMaxWidth()) {
//                OutlinedTextField(
//                    value = amount,
//                    onValueChange = { amount = it },
//                    label = { Text("Amount") },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    modifier = Modifier.fillMaxWidth()
//                )
//                OutlinedTextField(
//                    value = note,
//                    onValueChange = { note = it },
//                    label = { Text("Note") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Button(
//                    onClick = { showCalendar = true },
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                ) {
//                    Text(text = selectedDateFormatted)
//                }
//            }
//        }
//    )
//
//    if (showCalendar) {
//        Dialog(onDismissRequest = { showCalendar = false }) {
//            CustomCalendarDialog(
//                selectedDate = selectedDateObj,
//                onDismiss = { showCalendar = false },
//                onConfirm = { date ->
//                    selectedDateObj = date
//                    showCalendar = false
//                }
//            )
//        }
//    }
//}
