package com.neo.moneytracker.ui.screens

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.neo.moneytracker.ui.components.CalendarPickerButton
import com.neo.moneytracker.ui.components.SearchBox
import com.neo.moneytracker.ui.components.SearchSpec
import com.neo.moneytracker.ui.components.StickyFirstWithLazyRow
import com.neo.moneytracker.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    navController: NavHostController
) {
    var showSearch by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Money Tracker") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation icon click */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = {navController.navigate(Screens.searchScreen.route) }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }


                    CalendarPickerButton()

                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFE44C),
                    titleContentColor = Color.Black,    // Optional: Title text color
                    navigationIconContentColor = Color.Black,
                    actionIconContentColor = Color.Black

                )

            )
        },
        content = {paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFE44C))
                        .padding(0.dp)
                ) {
                    StickyFirstWithLazyRow()
                }
            }
        }
    )
}