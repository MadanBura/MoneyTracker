package com.neo.moneytracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.neo.moneytracker.ui.components.GenerateBackgroundIconColor
import com.neo.moneytracker.ui.components.SimpleTabLayout
import com.neo.moneytracker.ui.navigation.Screens
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.viewmodel.AddViewModel

@Composable
fun ManageCategoryScreen(navController: NavController) {
    val viewModel: AddViewModel = hiltViewModel()
    val categoryMap by viewModel.categoryMap
    val categories = categoryMap.keys.toList().dropLast(1)
    var selectedTabIndex by remember { mutableStateOf(0) }

    var selectedCategory = categories.getOrNull(selectedTabIndex)
    var subcategories = selectedCategory?.let { categoryMap[it] } ?: emptyList()

    LaunchedEffect(categoryMap) {
        selectedCategory = categories.getOrNull(selectedTabIndex)
        subcategories = selectedCategory?.let { categoryMap[it] } ?: emptyList()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(LemonSecondary)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Category settings",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(8.dp))

                SimpleTabLayout(
                    categories,
                    onTabSelected = {

                    },
                    modifier = Modifier
                        .height(40.dp)
                        .background(color = LemonSecondary)
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                )

            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                items(subcategories) { (name, iconRes) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .padding(top = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.RemoveCircle,
                            contentDescription = "Delete",
                            tint = Color.Red,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    // Handle delete
                                }
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        GenerateBackgroundIconColor(iconRes)

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Edit",
                            tint = Color.Gray,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    // Handle edit
                                }
                        )
                    }

                    HorizontalDivider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0F0F0))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screens.addCateogryScreen.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LemonSecondary,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Category")
                }

            }
        }
    }
}


//Before adding subcategory: {expenses=[(Groceries, 2131099702), (Dining Out, 2131099682), (Coffee, 2131099675), (Bars & Alcohol, 2131099652),
// (Rent, 2131099769), (Electricity, 2131099688), (Water, 2131099793), (Internet, 2131099661),
// (Maintenance, 2131099728), (Fuel, 2131099698), (Public Transport, 2131099788),
// (Taxi, 2131099666), (Car Maintenance, 2131099666), (Parking, 2131099751), (Medical, 2131099782), (Pharmacy, 2131099760), (Gym, 2131099794), (Salon, 2131099704), (Clothes, 2131099674), (Electronics, 2131099688), (Books, 2131099660), (Entertainment, 2131099689), (Flights, 2131099693), (Hotels, 2131099708), (Local Transport, 2131099726), (Touring, 2131099786), (Insurance, 2131099720), (Loans/EMI, 2131099725), (Credit Card, 2131099678), (Streaming Services, 2131099783), (Bank Fees, 2131099655), (Income Tax, 2131099719), (Savings, 2131099775), (Charity, 2131099668), (Presents, 2131099764), (Pet Care, 2131099759), (Personal Projects, 2131099757), (Uncategorized, 2131099790), (Settings, 2131099776)], income=[(Primary Paycheck, 2131099732), (Bonus, 2131099659), (Freelance, 2131099695), (Side Hustle, 2131099753), (Dividends, 2131099683), (Interest, 2131099722), (Cash Gifts, 2131099699), (Reimbursement, 2131099768), (Settings, 2131099776)], transfer=[(Bank Transfer, 2131099787), (UPI, 2131099754), (Settings, 2131099776)]}
//2025-07-18 18:26:23.889 29313-29313
// After adding subcategory: {expenses=[(neogames, 2131099804), (Groceries, 2131099702), (Dining Out, 2131099682), (Coffee, 2131099675), (Bars & Alcohol, 2131099652), (Rent, 2131099769), (Electricity, 2131099688), (Water, 2131099793), (Internet, 2131099661), (Maintenance, 2131099728), (Fuel, 2131099698), (Public Transport, 2131099788), (Taxi, 2131099666), (Car Maintenance, 2131099666), (Parking, 2131099751), (Medical, 2131099782), (Pharmacy, 2131099760), (Gym, 2131099794), (Salon, 2131099704), (Clothes, 2131099674), (Electronics, 2131099688), (Books, 2131099660), (Entertainment, 2131099689), (Flights, 2131099693), (Hotels, 2131099708), (Local Transport, 2131099726), (Touring, 2131099786), (Insurance, 2131099720), (Loans/EMI, 2131099725), (Credit Card, 2131099678), (Streaming Services, 2131099783), (Bank Fees, 2131099655), (Income Tax, 2131099719), (Savings, 2131099775), (Charity, 2131099668), (Presents, 2131099764), (Pet Care, 2131099759), (Personal Projects, 2131099757), (Uncategorized, 2131099790), (Settings, 2131099776)], income=[(Primary Paycheck, 2131099732), (Bonus, 2131099659), (Freelance, 2131099695), (Side Hustle, 2131099753), (Dividends, 2131099683), (Interest, 2131099722), (Cash Gifts, 2131099699), (Reimbursement, 2131099768), (Settings, 2131099776)], transfer=[(Bank Transfer, 2131099787), (UPI, 2131099754), (Settings, 2131099776)]}
