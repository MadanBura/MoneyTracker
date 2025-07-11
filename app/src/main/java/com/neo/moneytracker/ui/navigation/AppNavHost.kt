package com.neo.moneytracker.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.neo.moneytracker.ui.components.BottomNavigationBar
import com.neo.moneytracker.ui.screens.ReportsScreen

@Composable
fun AppNavHost(navHostController: NavHostController) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        SealedBottomNavItem.records.route,
        SealedBottomNavItem.charts.route,
        SealedBottomNavItem.add.route,
        SealedBottomNavItem.reports.route,
        SealedBottomNavItem.me.route
    )


    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navHostController)
            }
        }
    ) {
        NavHost(
            navController = navHostController,
            startDestination = SealedBottomNavItem.records.route,
            modifier = Modifier.padding(it)
        ) {
            composable(Screens.records.route) {
                ReportsScreen()
            }
        }
    }
}