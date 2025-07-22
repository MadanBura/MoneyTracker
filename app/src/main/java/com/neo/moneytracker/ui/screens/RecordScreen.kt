package com.neo.moneytracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.data.mapper.toDomainModel
import com.neo.moneytracker.ui.components.*
import com.neo.moneytracker.ui.navigation.Screens
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.viewmodel.TransactionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun RecordScreen(
    navController: NavHostController,
    transactionViewModel: TransactionViewModel
) {
    var isDrawerOpen by remember { mutableStateOf(false) }
    var selectedBook by remember { mutableStateOf("Default") }

    val transactionList by transactionViewModel.transactions.collectAsState(initial = emptyList())
    val groupedTransactions = transactionList.groupBy { it.date }

    val totalIncomeAmount by transactionViewModel.incomeTotalAmount.collectAsState()
    val expenseAmount by transactionViewModel.expensesTotalAmount.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    // For delete confirmation dialog state
    var transactionToDelete by remember { mutableStateOf<TransactionEntity?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Money Tracker") },
                navigationIcon = {
                    IconButton(onClick = { isDrawerOpen = !isDrawerOpen }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screens.searchScreen.route) }) {
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
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                if (isDrawerOpen) {
                    DrawerSection(
                        selectedBook = selectedBook,
                        onSelectBook = {
                            selectedBook = it
                            isDrawerOpen = false
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .background(LemonSecondary)
                        .fillMaxWidth()
                ) {
                    StickyFirstWithLazyRow(
                        expenseAmount = expenseAmount.toInt(),
                        incomeAmount = totalIncomeAmount.toInt()
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
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

                        items(
                            items = transactionsList,
                            key = { if (it.id != 0) it.id else it.hashCode() }
                        ) { transaction ->

                            val swipeableState = rememberSwipeableState(0)
                            val coroutineScope = rememberCoroutineScope()
                            val actionWidth = 160.dp
                            val actionPx = with(LocalDensity.current) { actionWidth.toPx() }
                            val anchors = mapOf(0f to 0, -actionPx to 1) // 0 = closed, 1 = open

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .swipeable(
                                        state = swipeableState,
                                        anchors = anchors,
                                        thresholds = { _, _ -> FractionalThreshold(0.3f) },
                                        orientation = Orientation.Horizontal
                                    )
                            ) {
                                // Background with Edit and Delete buttons
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Transparent),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextButton(
                                        onClick = {
                                            coroutineScope.launch { swipeableState.animateTo(0) }
                                            navController.navigate("add_screen?transactionId=${transaction.id}&isEdit=true")
                                        },
                                        modifier = Modifier
                                            .testTag("DeleteButton_${transaction.category}_${transaction.amount}")
                                            .width(80.dp)
                                            .fillMaxHeight()
                                            .background(Color(0xFFFFEB3B)) // Yellow
                                    ) {
                                        Text("Edit", color = Color.Black)
                                    }
                                    TextButton(
                                        onClick = {
                                            // Show confirmation dialog
                                            transactionToDelete = transaction
                                            coroutineScope.launch { swipeableState.animateTo(0) }
                                        },
                                        modifier = Modifier
                                            .width(80.dp)
                                            .fillMaxHeight()
                                            .background(Color.Red)
                                    ) {
                                        Text("Delete", color = Color.White)
                                    }
                                }

                                // Foreground transaction content that slides
                                Box(
                                    modifier = Modifier
                                        .testTag("TransactionItem_${transaction.category}_${transaction.amount}")
                                        .offset {
                                            IntOffset(
                                                swipeableState.offset.value.roundToInt(),
                                                0
                                            )
                                        }
                                        .fillMaxWidth()
                                        .background(Color.White, shape = RoundedCornerShape(2.dp))
                                        .padding(vertical = 8.dp, horizontal = 12.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        TransactionIcon(transaction.toDomainModel())
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(start = 8.dp)
                                        ) {
                                            Text(
                                                text = transaction.category,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Text(
                                            text = "₹${transaction.amount}",
                                            fontSize = 16.sp,
                                            modifier = Modifier.padding(start = 8.dp)
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

    // Confirmation dialog outside LazyColumn so it overlays on top
    transactionToDelete?.let { transaction ->
        AlertDialog(
            modifier = Modifier.testTag("DeleteDialog"),
            onDismissRequest = { transactionToDelete = null },
            title = { Text("Confirm Deletion") },
            text = {
                Text(
                    text = "Are you sure you want to delete \"${transaction.category}\" of ₹${transaction.amount.toInt()}?",
                    modifier = Modifier.testTag("DeleteConfirmationText")
                )
            }
            ,
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            transactionViewModel.deleteTransaction(transaction.id)
                            delay(300)
                            transactionToDelete = null
                        }
                    },
                    modifier = Modifier.testTag("ConfirmButton")
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { transactionToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun DrawerSection(
    selectedBook: String,
    onSelectBook: (String) -> Unit
) {
    val drawerItems = listOf(
        Triple("Default", "Personal cash book", false),
        Triple("Team cash book", "Share with multiple people", true),
        Triple("Business cash book", "Suitable for business use", true)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        drawerItems.forEach { (title, subtitle, isVp) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectBook(title) }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Book, contentDescription = null)
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, fontWeight = FontWeight.Bold)
                    Text(subtitle, fontSize = 12.sp, color = Color.Gray)
                }
                if (isVp) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("V/P", color = Color(0xFFFFC107), fontSize = 10.sp)
                        Spacer(Modifier.width(4.dp))
                        Icon(Icons.Default.Lock, contentDescription = "Locked", tint = Color.Gray)
                    }
                } else if (title == selectedBook) {
                    Icon(Icons.Default.Check, contentDescription = "Selected", tint = Color.Black)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /* Add V/P logic */ },
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
