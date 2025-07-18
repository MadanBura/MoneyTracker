package com.neo.moneytracker.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.neo.moneytracker.R
import com.neo.moneytracker.domain.model.AddAccount
import com.neo.moneytracker.ui.viewmodel.AccountsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccountScreen(
    navController: NavController,
    accountViewModel: AccountsViewModel,
    accountId: Int,
    modifier: Modifier = Modifier
) {
    Log.d("HELLO", "$accountId")
    accountViewModel.getAccountById(accountId)

    val account by accountViewModel.singleAccount.collectAsState()

    var accountName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf(0) }
    var accountNote by remember { mutableStateOf("") }

    LaunchedEffect(account) {
        account?.let {
            accountName = it.accountName
            amount = it.amount.toString()
            selectedType = it.type
            selectedCurrency = it.currency
            selectedIcon = it.icon
            accountNote = it.note
        }
    }

    val types = listOf(
        "Default", "Cash", "Debit Card", "Credit Card (Liabilities)",
        "Virtual account", "Investment", "Owes me/ Receivables",
        "I owe / Payables (Liabilities)"
    )
    val currencies = listOf("INR (₹) Indian Rupee", "USD ($) US Dollar", "EUR (€) Euro")
    val icons = listOf(
        R.drawable.cash, R.drawable.credit_card_discount, R.drawable.debit_card,
        R.drawable.dollar, R.drawable.pound, R.drawable.euro, R.drawable.paypal,
        R.drawable.bitcoin, R.drawable.percentage_bag, R.drawable.bank,
        R.drawable.profit_chart, R.drawable.mobile_wallet, R.drawable.wallet,
        R.drawable.piggy_bank, R.drawable.safe
    )

    val liabilityTypes = setOf(
        "Credit Card (Liabilities)", "I owe / Payables (Liabilities)"
    )
    val liabilities = selectedType in liabilityTypes

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Edit Account")
                    }
                },
                navigationIcon = {
                    Text(
                        "Cancel",
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                },
                actions = {
                    IconButton(onClick = {
                        accountViewModel.updateAccount(
                            AddAccount(
                                accountName = accountName,
                                type = selectedType,
                                currency = selectedCurrency,
                                amount = amount.toLongOrNull() ?: 0,
                                icon = selectedIcon,
                                liabilities = liabilities,
                                note = accountNote
                            )
                        )
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFFD54F))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LabeledTextField("Account name", accountName) { accountName = it }
            DropdownField("Type", selectedType, types) { selectedType = it }
            DropdownField("Currency", selectedCurrency, currencies) { selectedCurrency = it }
            LabeledTextField("Amount", amount, keyboardType = KeyboardType.Number) {
                amount = it
            }

            Text("Icon", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, RoundedCornerShape(20.dp))
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    contentPadding = PaddingValues(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    itemsIndexed(icons) { index, icon ->
                        IconToggleButton(
                            checked = selectedIcon == index,
                            onCheckedChange = { selectedIcon = index },
                            modifier = Modifier.padding(2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(
                                        if (selectedIcon == index) Color(0xFFFFD54F)
                                        else Color.LightGray
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(icon),
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }

            OutlinedTextField(
                value = accountNote,
                onValueChange = { accountNote = it },
                label = { Text("Note") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
