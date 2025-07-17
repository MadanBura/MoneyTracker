package com.neo.moneytracker.ui.screens

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DoNotDisturbOn
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.neo.moneytracker.R
import com.neo.moneytracker.ui.navigation.Screens
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.theme.YellowOrange
import com.neo.moneytracker.ui.viewmodel.AccountsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageAccounts(
    addAccountViewModel:AccountsViewModel,
    navController: NavHostController
) {

    val list = addAccountViewModel.accounts.collectAsState().value


    Log.d("HELLO", list.toString())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Manage Accounts",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LemonSecondary
                )
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp)
            ) {
                items(list.size) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DoNotDisturbOn,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.clickable {
                                addAccountViewModel.delAccount(list[index])
                            }
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.account_add),
                            contentDescription = null,
                            modifier = Modifier
                                .size(35.dp)
                                .padding(5.dp)
                        )


                        Column {
                            Text(
                                text = list[index].accountName,
                                modifier = Modifier.padding(start = 10.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = list[index].note,
                                modifier = Modifier.padding(start = 10.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = null
                            )
                            Icon(
                                imageVector = Icons.Filled.PushPin,
                                contentDescription = null
                            )
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = null
                            )

                        }
                    }

                }
            }


            Button(
                onClick = {
                    navController.navigate(Screens.addAccount.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LemonSecondary,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.LightGray
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "+ Add")
            }
        }
    }
}
