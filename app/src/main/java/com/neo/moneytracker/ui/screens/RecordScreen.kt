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
import androidx.compose.material.SwipeableState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    navController: NavController,
    transactionViewModel: TransactionViewModel
) {
    var isDrawerOpen by remember { mutableStateOf(false) }
    var selectedBook by remember { mutableStateOf("Default") }
    var isCalendarVisible by remember { mutableStateOf(false) }

    val transactions by transactionViewModel.transactions.collectAsState()
    val incomeAmount by transactionViewModel.incomeTotalAmount.collectAsState()
    val expenseAmount by transactionViewModel.expensesTotalAmount.collectAsState()

    val groupedTransactions = transactions.groupBy { it.date }

    val coroutineScope = rememberCoroutineScope()
    val swipeStates = remember { mutableStateMapOf<Int, SwipeableState<Int>>() }

    var transactionToDelete by remember { mutableStateOf<TransactionEntity?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

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
                    if (!isDrawerOpen) {
                        IconButton(onClick = {
                            navController.navigate(Screens.searchScreen.route)
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                        IconButton(onClick = {
                            isCalendarVisible = true
                        }) {
                            Icon(Icons.Default.CalendarToday, contentDescription = "Calendar")
                        }

                    }
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
                    return@Column
                }

                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .background(LemonSecondary)
                            .fillMaxWidth()
                    ) {
                        StickyFirstWithLazyRow(
                            incomeAmount = incomeAmount.toInt(),
                            expenseAmount = expenseAmount.toInt()
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        groupedTransactions.forEach { (date, txList) ->
                            item {
                                DailySummaryRow(date, transactionViewModel)
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                            }

                            items(txList, key = { it.id }) { transaction ->
                                val swipeState = swipeStates.getOrPut(transaction.id) {
                                    rememberSwipeableState(0)
                                }

                                val actionWidth = 160.dp
                                val actionPx = with(LocalDensity.current) { actionWidth.toPx() }
                                val anchors = mapOf(0f to 0, -actionPx to 1)

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .swipeable(
                                            state = swipeState,
                                            anchors = anchors,
                                            thresholds = { _, _ -> FractionalThreshold(0.3f) },
                                            orientation = Orientation.Horizontal
                                        )
                                ) {
                                    // Swipe background
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.White),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        TextButton(
                                            onClick = {
                                                coroutineScope.launch { swipeState.animateTo(0) }
                                                navController.navigate("add_screen?transactionId=${transaction.id}&isEdit=true")
                                            },
                                            modifier = Modifier
                                                .width(80.dp)
                                                .fillMaxHeight()
                                                .background(Color(0xFFFFEB3B))
                                        ) {
                                            Text("Edit", color = Color.Black)
                                        }
                                        TextButton(
                                            onClick = {
                                                transactionToDelete = transaction
                                                showDeleteDialog = true
                                                coroutineScope.launch { swipeState.animateTo(0) }
                                            },
                                            modifier = Modifier
                                                .testTag("DeleteButton_${transaction.category}_${transaction.amount}")
                                                .width(80.dp)
                                                .fillMaxHeight()
                                                .background(Color.Red)
                                        ) {
                                            Text("Delete", color = Color.White)
                                        }
                                    }

                                    // Swipeable Foreground
                                    Box(
                                        modifier = Modifier
                                            .testTag("TransactionItem_${transaction.category}_${transaction.amount}")
                                            .offset {
                                                IntOffset(
                                                    swipeState.offset.value.roundToInt(),
                                                    0
                                                )
                                            }
                                            .fillMaxWidth()
                                            .background(
                                                Color.White,
                                                shape = RoundedCornerShape(2.dp)
                                            )
                                            .padding(12.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            TransactionIcon(transaction.toDomainModel())
                                            Column(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(start = 8.dp)
                                            ) {
                                                Text(
                                                    text = transaction.category,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp
                                                )
                                            }
                                            Text(
                                                text = "₹${transaction.amount}",
                                                fontSize = 16.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (isCalendarVisible) {
                    CustomCalendarDialog(
                        onDateSelected = { selectedDate ->
                            isCalendarVisible = false
                        },
                        onDismiss = {
                            isCalendarVisible = false
                        },
                        isCompact = true
                    )
                }
                // Delete confirmation dialog
                if (showDeleteDialog && transactionToDelete != null) {
                    AlertDialog(
                        modifier = Modifier.testTag("DeleteDialog"),
                        onDismissRequest = {
                            coroutineScope.launch {
                                val transaction = transactionToDelete
                                if (transaction != null) {
                                    swipeStates[transaction.id]?.animateTo(0)
                                }
                                transactionToDelete = null
                                showDeleteDialog = false
                            }
                        }
                        ,
                        title = { Text("Delete Transaction") },
                        text = {
                            Text("Are you sure you want to delete \"${transactionToDelete!!.category}\" of ₹${transactionToDelete!!.amount}?")
                        },
                        confirmButton = {
                            TextButton(
                                modifier = Modifier.testTag("ConfirmButton"),
                                onClick = {
                                    coroutineScope.launch {
                                        transactionViewModel.deleteTransaction(transactionToDelete!!.id)
                                        delay(250)
                                        swipeStates.remove(transactionToDelete!!.id)
                                        transactionToDelete = null
                                        showDeleteDialog = false
                                    }
                                }
                            ) {
                                Text("Delete")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    coroutineScope.launch {
                                        val transaction = transactionToDelete
                                        if (transaction != null) {
                                            swipeStates[transaction.id]?.animateTo(0)
                                        }
                                        transactionToDelete = null
                                        showDeleteDialog = false
                                    }
                                }
                            ) {
                                Text("Cancel")
                            }
                        }

                    )
                }
            }
        }
    }
}