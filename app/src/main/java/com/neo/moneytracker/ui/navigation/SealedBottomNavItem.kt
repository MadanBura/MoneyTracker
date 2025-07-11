package com.neo.moneytracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.neo.moneytracker.R

sealed class SealedBottomNavItem(
    val route: String,
    val icon: Int,
    val label: String
) {
    object records : SealedBottomNavItem("records", R.drawable.records, "Dashboard")
    object charts : SealedBottomNavItem("charts", R.drawable.chart, "Search")
    object add : SealedBottomNavItem("add", R.drawable.add, "Cart")
    object reports : SealedBottomNavItem("reports",R.drawable.reports, "Profile")
    object me : SealedBottomNavItem("me", R.drawable.me, "Profile")
}
