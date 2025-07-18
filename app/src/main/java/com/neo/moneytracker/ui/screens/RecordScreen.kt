package com.neo.moneytracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.neo.moneytracker.data.mapper.toDomainModel
import com.neo.moneytracker.ui.components.CalendarPickerButton
import com.neo.moneytracker.ui.components.DailySummaryRow
import com.neo.moneytracker.ui.components.StickyFirstWithLazyRow
import com.neo.moneytracker.ui.components.TransactionIcon
import com.neo.moneytracker.ui.navigation.Screens
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.viewmodel.TransactionViewModel
import com.neo.moneytracker.utils.TransactionType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(navController: NavHostController, transactionViewModel: TransactionViewModel) {
    var isDrawerOpen by remember { mutableStateOf(false) }
    var selectedBook by remember { mutableStateOf("Default") }

    val transactionList by transactionViewModel.transactions.collectAsState(initial = emptyList())

    val groupedTransactions = transactionList.groupBy { transaction ->
        transaction.date
    }

    val totalIncomeAmount = transactionViewModel.incomeTotalAmount.collectAsState()
    var expenseAmount = transactionViewModel.expensesTotalAmount.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Money Tracker") },
                navigationIcon = {
                    IconButton(onClick = {
                        isDrawerOpen = !isDrawerOpen
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screens.searchScreen.route)
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    CalendarPickerButton()
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = LemonSecondary,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        },
        content = { paddingValues ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isDrawerOpen) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        val drawerItems = listOf(
                            Triple("Default", "Personal cash book", false),
                            Triple("Team cash book", "Share with multiple people", true),
                            Triple("Business cash book", "Suitable for business use", true)
                        )

                        drawerItems.forEach { (title, subtitle, isVp) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedBook = title
                                        isDrawerOpen = false
                                    }
                                    .padding(vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Book, contentDescription = null)
                                Spacer(Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(title, fontWeight = FontWeight.Bold)
                                    Text(subtitle, fontSize = 12.sp, color = Color.Gray)
                                }
                                if (isVp) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("V/P", color = Color(0xFFFFC107), fontSize = 10.sp)
                                        Spacer(Modifier.width(4.dp))
                                        Icon(
                                            Icons.Default.Lock,
                                            contentDescription = "Locked",
                                            tint = Color.Gray
                                        )
                                    }
                                } else if (title == selectedBook) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = Color.Black
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.LightGray,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            ) {
                                Text("Add")
                                Text(" V/P", color = Color(0xFFFFC107))
                            }

                            Button(
                                onClick = { /* Join logic */ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.LightGray,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Join")
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Box(
                        modifier = Modifier
                            .background(LemonSecondary)
                            .fillMaxWidth()
                    ) {
                        StickyFirstWithLazyRow(
                            expenseAmount = expenseAmount.toInt(),
                            incomeAmount = totalIncomeAmount.value.toInt()
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        groupedTransactions.forEach { (date, transactionsList) ->
                            item {

                                DailySummaryRow(date, transactionViewModel)
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                            }

                            items(transactionsList) { transaction ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                        .background(Color.White, shape = RoundedCornerShape(8.dp)),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TransactionIcon(transaction.toDomainModel())

                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = transaction.category,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                    }

                                    Text(
                                        text = "${transaction.amount}",
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        modifier = Modifier
                                            .padding(8.dp)
                                    )


                                }
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 1.dp)
                                )

                            }
                        }
                    }
                }
            }
        }
    )
}