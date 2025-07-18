package com.neo.moneytracker.ui.screens

import androidx.compose.foundation.background
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
    val categories = categoryMap.keys.toList().dropLast(1) // e.g., Income, Expenses
    var selectedTabIndex by remember { mutableStateOf(0) }

    val selectedCategory = categories.getOrNull(selectedTabIndex)
    val subcategories = selectedCategory?.let { categoryMap[it] } ?: emptyList()

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

                SimpleTabLayout(categories) { selectedCategory ->
                    selectedTabIndex = categories.indexOf(selectedCategory)
                }
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
