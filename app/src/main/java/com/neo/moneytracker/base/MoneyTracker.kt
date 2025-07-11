package com.neo.moneytracker.base

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.neo.moneytracker.ui.navigation.AppNavHost

@Composable
fun MoneyTracker() {
    val navController = rememberNavController()
    AppNavHost(navController)
}