package com.neo.moneytracker.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.neo.moneytracker.ui.screens.AddScreen
import com.neo.moneytracker.ui.screens.ChartScreen
import com.neo.moneytracker.ui.screens.MeScreen
import com.neo.moneytracker.ui.screens.RecordScreen
import com.neo.moneytracker.ui.screens.ReportScreen

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
            composable(SealedBottomNavItem.records.route) {
               RecordScreen()
            }

            composable(SealedBottomNavItem.charts.route) {
                ChartScreen()
            }

            composable(SealedBottomNavItem.add.route) {
                AddScreen()
            }

            composable(SealedBottomNavItem.reports.route) {
                ReportScreen()
            }
            composable(SealedBottomNavItem.me.route) {
                MeScreen()
            }


        }
    }
}
