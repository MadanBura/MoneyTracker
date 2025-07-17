package com.neo.moneytracker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.neo.moneytracker.R
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.ui.navigation.Screens
import com.neo.moneytracker.ui.navigation.SealedBottomNavItem
import com.neo.moneytracker.ui.theme.IconBackGroundColor
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.viewmodel.TransactionViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AmountInputDialog(
    iconRes: Int,
    selectedCategory: String,
    transactionViewModel: TransactionViewModel,
    navController: NavController
) {
    var note by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("0") }

    val keypadButtons = listOf(
        listOf("7", "8", "9", "Today"),
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
                    modifier = Modifier
                        .size(30.dp)
                )

                Text(
                    text = amount,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(end = 4.dp),
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
                        disabledContainerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = { /* handle camera click */ }) {
                    Image(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = "Camera Icon",
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            // Keypad
            keypadButtons.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    row.forEach { label ->
                        Button(
                            onClick = {
                                when (label) {
                                    "⌫" -> {
                                        amount = if (amount.length > 1) amount.dropLast(1) else "0"
                                    }

                                    "✔" -> {

                                    }

                                    "Today" -> {
                                        // Add date picker
                                    }

                                    "+", "-" -> {
                                        // Optional math
                                    }

                                    "." -> if (!amount.contains(".")) amount += "."
                                    else -> {
                                        amount = if (amount == "0") label else amount + label
                                    }
                                }
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .weight(1f)
                                .padding(horizontal = 1.dp, vertical = 3.dp),
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = when (label) {
                                    "✔" -> LemonSecondary
                                    else -> Color.White
                                }
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            when (label) {
                                "Today" -> {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.calendar), // Replace with your calendar icon
                                            contentDescription = "Calendar Icon",
                                            tint = Color(0xFFFFC107),
                                            modifier = Modifier
                                                .size(18.dp)
                                                .padding(end = 2.dp)
                                        )
                                        Text(
                                            text = label,
                                            fontSize = 14.sp, // smaller font size
                                            color = Color(0xFFFFC107),
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }

                                "✔" -> {

                                    val currentDate =
                                        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                                            Date()
                                        )

                                    val transaction = TransactionEntity(
                                        iconRes = iconRes,
                                        amount = amount,
                                        note = note,
                                        date = currentDate,
                                        category = selectedCategory
                                    )

                                    transactionViewModel.addTransaction(transaction)
                                    amount = "0"
                                    note = ""

                                    navController.navigate(SealedBottomNavItem.records.route) {
                                        popUpTo(SealedBottomNavItem.records.route) { inclusive = false }
                                    }

                                    Icon(
                                        painter = painterResource(id = R.drawable.check_),
                                        contentDescription = "Check Icon",
                                        tint = Color.Black,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                else -> {
                                    Text(
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
    }
}
