package com.neo.moneytracker.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults as CustomTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.neo.moneytracker.R
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.viewmodel.AddViewModel
import com.neo.moneytracker.utils.CategoryIconList
import com.neo.moneytracker.utils.toSentenceCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryScreen(
    navController: NavController
) {
    val viewModel: AddViewModel = hiltViewModel()
    val categoryMap by viewModel.categoryMap
    val categories = categoryMap.keys.toList().dropLast(1)
    var selectedTabIndex by remember { mutableStateOf(0) }
    val selectedCategory = categories.getOrNull(selectedTabIndex)

    val categoriesMap = CategoryIconList.imageCategoryMap
    var selectedICon by remember {
        mutableStateOf(
            categoriesMap.values.firstOrNull()?.firstOrNull() ?: 0
        )
    }

    var enterCategoryName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top App Bar
        CenterAlignedTopAppBar(
            modifier = Modifier.background(LemonSecondary),
            title = {
                Text(
                    text = "Add category",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Cancel", color = Color.Black)
                }
            },
            actions = {
                Image(
                    painter = painterResource(id = R.drawable.check_),
                    contentDescription = "Done",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            if (enterCategoryName.isNotBlank() && selectedCategory != null) {
                                Log.d(
                                    "ADDCATEGORYSCREEN", "$selectedCategory,\n" +
                                            "                                    $enterCategoryName,\n" +
                                            "                                    $selectedICon"
                                )
                                viewModel.addSubcategory(
                                    selectedCategory,
                                    enterCategoryName,
                                    selectedICon
                                )
                                navController.popBackStack()
                            }
                        }
                        .padding(end = 8.dp)
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = LemonSecondary)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Tab Layout (Expense / Income)
        Row(
            modifier = Modifier
                .height(40.dp)
                .padding(horizontal = 16.dp, vertical = 2.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
        ) {
            categories.forEachIndexed { index, tab ->
                val isSelected = index == selectedTabIndex
                val shape = when (index) {
                    0 -> RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                    categories.lastIndex -> RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                    else -> RoundedCornerShape(0.dp)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(shape)
                        .background(if (isSelected) Color.Black else Color.White)
                        .clickable { selectedTabIndex = index }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) Color(0xFFFFEB3B) else Color.Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(LemonSecondary)
            ) {
                Icon(
                    painter = painterResource(selectedICon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = enterCategoryName,
                onValueChange = { enterCategoryName = it },
                placeholder = { Text("Please enter the category name") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp)),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFEEEEEE),
                    focusedContainerColor = Color(0xFFEEEEEE),
                    disabledContainerColor = Color(0xFFEEEEEE),
                    unfocusedIndicatorColor = Color(0xFFEEEEEE),
                    focusedIndicatorColor = Color(0xFFEEEEEE),
                    disabledIndicatorColor = Color(0xFFEEEEEE),
                    errorIndicatorColor = Color(0xFFEEEEEE)
                )
            )
        }


        Spacer(modifier = Modifier.height(16.dp))
        // Category Icon List Section
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            categoriesMap.forEach { (categoryName, iconList) ->
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .wrapContentHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = categoryName.toSentenceCase(),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                // Grid of icons/images for this category
                item {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5),
                        contentPadding = PaddingValues(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(0.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .wrapContentHeight(),
                        userScrollEnabled = false
                    ) {
                        items(iconList) { icon ->
                            IconSelectable(
                                icon, isSelected = icon == selectedICon, onClick = {
                                    selectedICon = icon
                                }
                            )
                        }
                    }
                }
            }


        }
    }
}

// Icon selection composable
@Composable
fun IconSelectable(iconRes: Int, isSelected: Boolean, onClick: () -> Unit) {

    val backgroundColor = if (isSelected) LemonSecondary else Color(0xFFEEEEEE)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(10.dp)
            .size(56.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() }
    ) {
//        val painter = rememberAsyncImagePainter("file:///android_asset/$path")
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}
