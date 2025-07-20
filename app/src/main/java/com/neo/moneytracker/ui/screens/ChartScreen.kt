package com.neo.moneytracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.neo.moneytracker.data.mapper.toDomainModel
import com.neo.moneytracker.ui.components.SimpleTabLayout
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.viewmodel.TransactionViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChartScreen() {
    val transactionViewModel: TransactionViewModel = hiltViewModel()
    var selectedTab by remember { mutableStateOf("Month") }
    val tabs = listOf("Month", "Year")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Expenses") }
    val options = listOf("Expenses", "Income")

    val transactions by transactionViewModel.transactions.collectAsState()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(LemonSecondary)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = selectedOption,
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown",
                        tint = Color.Black,
                        modifier = Modifier.clickable {
                            expanded = true
                        }
                    )
                    if (expanded) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            options.forEach { option ->
                                androidx.compose.material3.DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        selectedOption = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }



                Spacer(modifier = Modifier.padding(bottom = 4.dp))
                SimpleTabLayout(
                    tabs = tabs,
                    onTabSelected = { tabName ->
                        selectedTab = tabName
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .background(color = LemonSecondary)
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.padding(bottom = 6.dp))
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Main screen content below tabs
//            ScrollableMonthRow()
            when (selectedTab) {
                "Month" -> MonthScreen(transactions)
                "Year" -> YearScreen(transactions)
            }
        }
    }

}
