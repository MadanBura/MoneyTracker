package com.neo.moneytracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.neo.moneytracker.R
import com.neo.moneytracker.ui.components.AmountInputDialog
import com.neo.moneytracker.ui.components.SimpleTabLayoutforAdd
import com.neo.moneytracker.ui.navigation.Screens
import com.neo.moneytracker.ui.navigation.SealedBottomNavItem
import com.neo.moneytracker.ui.theme.IconBackGroundColor
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.viewmodel.AccountsViewModel

@Composable
fun AddScreen(
    navController: NavController,
    accountViewModel: AccountsViewModel,
    transactionId: Int? = null,
    isEdit: Boolean = false
) {
    val viewModel: AccountsViewModel = hiltViewModel()
    //    val context = LocalContext.current
    viewModel.loadCategories()
    val categoryMap by viewModel.categoryMap
//    val categoryMap = remember { ReaderHelper.loadFullCategoryMap(context) }
    val categories = categoryMap.keys.toList()

    var selectedTabIndex by remember { mutableStateOf(0) }
    val selectedCategory = categories.getOrNull(selectedTabIndex) ?: return

    var selectedItem by remember { mutableStateOf<String?>(null) }
    var selectedIcon by remember { mutableStateOf<Int?>(null) }
    val dialogVisible by accountViewModel.isDialogVisible.collectAsState()

    val transactions = accountViewModel.transactions.collectAsState().value
    val transactionToEdit = remember(transactionId, transactions) {
        transactionId?.let { id -> transactions.find { it.id == id } }
    }

    LaunchedEffect(transactionId, accountViewModel.transactions.collectAsState().value) {
        println("Transactions available: ${accountViewModel.transactions.value}")
        println("Looking for ID: $transactionId")
    }

    LaunchedEffect(transactionToEdit, isEdit) {
        if (isEdit) {
            transactionToEdit?.let { tx ->
                selectedTabIndex = categories.indexOf(tx.category).takeIf { it >= 0 } ?: 0
                selectedItem = tx.category
                selectedIcon = tx.iconRes
                accountViewModel.setDialogVisible(true)
            }
        }
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
                    TextButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Text("Cancel", color = Color.Black)
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Add",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.checklist),
                        contentDescription = "Settings Icon",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 8.dp)
                    )
                }

                SimpleTabLayoutforAdd(
                    tabs = categories,
                    selectedIndex = selectedTabIndex,
                    onTabSelected = {
                        selectedTabIndex = it
                        selectedItem = null // Reset on tab change
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .background(color = LemonSecondary)
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.height(6.dp))
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
        ) {
            val subWithIcons = categoryMap[selectedCategory] ?: emptyList()

            Column(modifier = Modifier.weight(1f)) {
                SubcategoryGridScreen(
                    subWithIcons = subWithIcons,
                    selectedItem = selectedItem
                ) { clickedItem, iconRes ->
                    if (clickedItem == "Settings") {
                        navController.navigate(Screens.settings.route)
                    } else {
                        selectedItem = clickedItem
                        selectedIcon = iconRes

                        accountViewModel.setDialogVisible(true)
                    }
                }
            }

            if (dialogVisible && selectedItem != null && selectedItem != "Settings") {
                AmountInputDialog(
                    iconRes = selectedIcon!!,
                    selectedCategory = selectedCategory,
                    selectedSubCategory = selectedItem!!,
                    onTransactionAdded = { newTransaction ->
                        if (isEdit && transactionToEdit != null) {
                            val updated = newTransaction.copy(id = transactionToEdit!!.id)
                            accountViewModel.updateTransaction(updated)
                        } else {
                            accountViewModel.addTransaction(newTransaction)
                        }
                    },
                    onDismiss = {
                        accountViewModel.setDialogVisible(false)
                        selectedItem = null
                        navController.popBackStack(
                            SealedBottomNavItem.records.route,
                            inclusive = false
                        )
                    },
                    accountViewModel = accountViewModel,
                    initialAmount = transactionToEdit?.amount.orEmpty(),
                    initialDate = transactionToEdit?.date ?: "",
                    initialNote = transactionToEdit?.note ?: ""
                )
            }
        }
    }
}

@Composable
fun SubcategoryGridScreen(
    subWithIcons: List<Pair<String, Int>>,
    selectedItem: String?,
    onSubcategoryClick: (String, Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(subWithIcons) { (name, iconRes) ->
            val isSelected = selectedItem == name
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(4.dp)
                    .selectable(
                        selected = isSelected,
                        onClick = { onSubcategoryClick(name, iconRes) },
                        role = Role.Image
                    )
                    .testTag("subcategory$name")
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            color = if (isSelected) LemonSecondary else IconBackGroundColor,
                            shape = CircleShape
                        ),
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