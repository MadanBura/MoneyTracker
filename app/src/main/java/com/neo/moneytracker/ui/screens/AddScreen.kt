package com.neo.moneytracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.neo.moneytracker.R
import com.neo.moneytracker.ui.components.SimpleTabLayoutforAdd
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.viewmodel.AddViewModel
import com.neo.moneytracker.utils.ReaderHelper

@Composable
fun AddScreen(navController: NavController) {
    val viewModel: AddViewModel = hiltViewModel()
    //    val context = LocalContext.current
    val categoryMap by viewModel.categoryMap
//    val categoryMap = remember { ReaderHelper.loadFullCategoryMap(context) }
    val categories = categoryMap.keys.toList()

    var selectedTabIndex by remember { mutableStateOf(0) }
    val selectedCategory = categories.getOrNull(selectedTabIndex) ?: return

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(LemonSecondary)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = {
                                navController.popBackStack()
                            },
                            modifier = Modifier.padding(start = 4.dp)
                        ) {
                            Text("Cancel", color = Color.Black)
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "Add",
                            color = Color.Black,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.weight(1.75f))
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                SimpleTabLayoutforAdd(
                    tabs = categories,
                    selectedIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it }
                )

                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            val subWithIcons = categoryMap[selectedCategory] ?: emptyList()
            SubcategoryGridScreen(subWithIcons)
        }
    }
}

@Composable
fun SubcategoryGridScreen(subWithIcons: List<Pair<String, Int>>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(subWithIcons) { (name, iconRes) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(color = Color(0xFFE0E0E0), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = name,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}


//
//val subcategoryIconMap = mapOf(
//    // Income - Salary
//    "Primary Paycheck" to R.drawable.money_check,
//    "Bonus" to R.drawable.bonus_,
//
//    // Income - Business
//    "Freelance" to R.drawable.freelance,
//    "Side Hustle" to R.drawable.parttime,
//
//    // Income - Investments
//    "Dividends" to R.drawable.dividends,
//    "Interest" to R.drawable.interest_rate,
//
//    // Income - Gifts & Refunds
//    "Cash Gifts" to R.drawable.gift,
//    "Reimbursement" to R.drawable.reimbursment,
//
//    // Expenses - Food & Drink
//    "Groceries" to R.drawable.grocery,
//    "Dining Out" to R.drawable.dinner_table,
//    "Coffee" to R.drawable.coffee_cup,
//    "Bars & Alcohol" to R.drawable.alcohol,
//
//    // Expenses - Home & Utilities
//    "Rent" to R.drawable.rent_signal,
//    "Electricity" to R.drawable.electronics,
//    "Water" to R.drawable.water,
//    "Internet" to R.drawable.browser,
//    "Maintenance" to R.drawable.maintenance,
//
//    // Expenses - Transport & Auto
//    "Fuel" to R.drawable.gas_station,
//    "Public Transport" to R.drawable.transportation,
//    "Taxi" to R.drawable.car,
//    "Car Maintenance" to R.drawable.car,
//    "Parking" to R.drawable.parking_area,
//
//    // Expenses - Health & Personal Care
//    "Medical" to R.drawable.stethoscope,
//    "Pharmacy" to R.drawable.pharmacy,
//    "Gym" to R.drawable.weight,
//    "Salon" to R.drawable.hair_cutting,
//
//    // Expenses - Shopping & Lifestyle
//    "Clothes" to R.drawable.clothing,
//    "Electronics" to R.drawable.electronics,
//    "Books" to R.drawable.books,
//    "Entertainment" to R.drawable.entertainment,
//
//    // Expenses - Travel
//    "Flights" to R.drawable.flights,
//    "Hotels" to R.drawable.hotels,
//    "Local Transport" to R.drawable.local_transport,
//    "Touring" to R.drawable.touring,
//
//    // Expenses - Bills & Subscriptions
//    "Insurance" to R.drawable.insurance,
//    "Loans/EMI" to R.drawable.loans,
//    "Credit Card" to R.drawable.credit_card,
//    "Streaming Services" to R.drawable.streaming_services,
//
//    // Expenses - Finance & Taxes
//    "Bank Fees" to R.drawable.bank_fees,
//    "Income Tax" to R.drawable.income_tax,
//    "Savings" to R.drawable.savings,
//
//    // Expenses - Gifts & Donations
//    "Charity" to R.drawable.charity,
//    "Presents" to R.drawable.presents,
//
//    // Expenses - Other
//    "Pet Care" to R.drawable.pet_care,
//    "Personal Projects" to R.drawable.personal_projects,
//    "Uncategorized" to R.drawable.uncategorized,
//
//    // Transfer
//    "Bank Transfer" to R.drawable.transference,
//    "UPI" to R.drawable.payment
//)


//@Composable
//fun CategoryGridWrapper(context: Context) {
//    val subcategoryToParentList = remember {
//        ReaderHelper.loadCategoryMapFromAssets(context)
//    }
//
//    val subWithIcons = subcategoryToParentList.map { (subcategory, _) ->
//        subcategory to (subcategoryIconMap[subcategory] ?: R.drawable.test)
//    }
//
//    SubcategoryGridScreen(subWithIcons)
//}