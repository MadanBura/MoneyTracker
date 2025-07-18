package com.neo.moneytracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.neo.moneytracker.ui.components.ReorderableSubCategoryList
import com.neo.moneytracker.ui.components.SimpleTabLayout
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.viewmodel.AddViewModel

@Composable
fun ManageCategoryScreen(navController: NavController) {
    val viewModel: AddViewModel = hiltViewModel()
    val categoryMap by viewModel.categoryMap
    val categories = categoryMap.keys.toList().dropLast(1)
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
                            .clickable { navController.popBackStack() }
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

                SimpleTabLayout(categories) { selected ->
                    selectedTabIndex = categories.indexOf(selected)
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
            ReorderableSubCategoryList(
                subcategories = subcategories,
                onDelete = { item ->
                    viewModel.removeSubCategory(selectedCategory ?: "", item)
                },
                onEdit = { item ->
                    // You can navigate or show dialog here
                },
                onReorder = { from, to ->
                    viewModel.reorderSubCategory(selectedCategory ?: "", from, to)
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0F0F0))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { navController.navigate(com.neo.moneytracker.ui.navigation.Screens.addCateogryScreen.route) },
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
