package com.neo.moneytracker.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun MoneyTrackerDrawerApp() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var selectedBook by remember { mutableStateOf("Default") }

    val drawerItems = listOf(
        Triple("Default", "Personal cash book", false),
        Triple("Team cash book", "Share with multiple people", true),
        Triple("Business cash book", "Suitable for business use", true)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.padding(16.dp)) {
                    drawerItems.forEach { (title, subtitle, isVip) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedBook = title
                                    coroutineScope.launch { drawerState.close() }
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Receipt, contentDescription = null)
                            Spacer(Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(title, fontWeight = FontWeight.Bold)
                                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
                            }
                            if (isVip) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("VIP", color = Color(0xFFFFC107), fontSize = 10.sp)
                                    Spacer(Modifier.width(4.dp))
                                    Icon(Icons.Default.Lock, contentDescription = "Locked", tint = Color.Gray)
                                }
                            } else if (title == selectedBook) {
                                Icon(Icons.Default.Check, contentDescription = "Selected", tint = Color.Black)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { /* Add */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f).padding(end = 8.dp)
                        ) {
                            Text("Add")
                            Text(" VIP", color = Color(0xFFFFC107))
                        }

                        Button(
                            onClick = { /* Join */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Join")
                        }
                    }
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Money Tracker") },
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScope.launch { drawerState.open() }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        },
                        actions = {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.CalendarToday, contentDescription = "Calendar")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFFFFD54F),
                            titleContentColor = Color.Black,
                            actionIconContentColor = Color.Black,
                            navigationIconContentColor = Color.Black
                        )
                    )
                },
                content = { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Selected: $selectedBook", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    )
}
