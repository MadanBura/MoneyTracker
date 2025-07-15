package com.neo.moneytracker.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavHostController
import com.neo.moneytracker.ui.components.BottomNavigationBar
import com.neo.moneytracker.ui.components.FabAddButton
import com.neo.moneytracker.ui.components.SearchSpec
import com.neo.moneytracker.ui.screens.AddScreen
import com.neo.moneytracker.ui.screens.ChartScreen
import com.neo.moneytracker.ui.screens.MeScreen
import com.neo.moneytracker.ui.screens.ReportScreen
import com.neo.moneytracker.ui.screens.RecordScreen


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
        },
        floatingActionButton = {
            if (showBottomBar) {
                FabAddButton(navController = navHostController)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    )
    { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = SealedBottomNavItem.records.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(SealedBottomNavItem.records.route) {
                RecordScreen(
                    navController = navHostController
                )
            }
            composable(Screens.searchScreen.route) {
                SearchSpec(
                    navController = navHostController
                )
            }

            composable(SealedBottomNavItem.charts.route) {
                ChartScreen()
            }

            composable(SealedBottomNavItem.add.route) {
                AddScreen(navHostController)
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

