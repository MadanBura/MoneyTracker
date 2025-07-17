package com.neo.moneytracker.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.neo.moneytracker.R
import com.neo.moneytracker.ui.navigation.Screens
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.viewmodel.AccountsViewModel

@Composable
fun AccountsScreen(
    navController: NavHostController,
    accountViewModel: AccountsViewModel,
    modifier: Modifier = Modifier
) {
    val assets = accountViewModel.assets.collectAsState().value
    val liabilities = accountViewModel.liabilities.collectAsState().value
    val netWorth = accountViewModel.netWorth.collectAsState().value

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        colors = CardDefaults.cardColors(containerColor = LemonSecondary)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Net Worth", color = Color.DarkGray)
                Text("$netWorth", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                Spacer(modifier = Modifier.height(8.dp))

                Text("Assets", color = Color.DarkGray)
                Text("$assets", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.money_bag),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Liabilities", color = Color.DarkGray)
                Text("$liabilities", fontWeight = FontWeight.Bold)
            }
        }
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AccountButton(
            "Add Account",
            modifier = Modifier.weight(1f),
            onClick = {
                navController.navigate(Screens.addAccount.route)
            }
        )
        AccountButton(
            "Manage Accounts",
            modifier = Modifier.weight(1f),
            onClick = { navController.navigate(Screens.manageAccount.route) }
        )
    }
}


@Composable
fun AccountButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = modifier
//            .weight(1f)
            .padding(horizontal = 2.dp)
    ) {
        Text(text = text, color = Color.Black)
    }
}