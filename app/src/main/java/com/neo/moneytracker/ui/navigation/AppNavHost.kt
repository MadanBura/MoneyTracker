package com.neo.moneytracker.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.neo.moneytracker.ui.components.BottomNavigationBar
import com.neo.moneytracker.ui.components.FabAddButton
import com.neo.moneytracker.ui.components.SearchSpec
import com.neo.moneytracker.ui.screens.AddAccountScreen
import com.neo.moneytracker.ui.screens.AddCategoryScreen
import com.neo.moneytracker.ui.screens.AddScreen
import com.neo.moneytracker.ui.screens.ChartScreen
import com.neo.moneytracker.ui.screens.EditAccountScreen
import com.neo.moneytracker.ui.screens.ManageAccounts
import com.neo.moneytracker.ui.screens.ManageCategoryScreen
import com.neo.moneytracker.ui.screens.MeScreen
import com.neo.moneytracker.ui.screens.ReportScreen
import com.neo.moneytracker.ui.screens.RecordScreen

import com.neo.moneytracker.ui.viewmodel.AccountsViewModel
import com.neo.moneytracker.ui.viewmodel.TransactionViewModel
import com.neo.moneytracker.ui.viewmodel.UiStateViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navHostController: NavHostController) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val uiStateViewModel: UiStateViewModel = hiltViewModel()
    val transactionViewModel : TransactionViewModel = hiltViewModel()
    val isDialogVisible by uiStateViewModel.isDialogVisible.collectAsState()

    val accountViewModel: AccountsViewModel = hiltViewModel()

    val showBottomBar = currentRoute in listOf(
        SealedBottomNavItem.records.route,
        SealedBottomNavItem.charts.route,
        SealedBottomNavItem.add.route,
        SealedBottomNavItem.reports.route,
        SealedBottomNavItem.me.route
    ) && !isDialogVisible

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
                    navController = navHostController, transactionViewModel
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
                AddScreen(navHostController, uiStateViewModel, transactionViewModel)
            }

            composable(SealedBottomNavItem.reports.route) {
                ReportScreen(
                    navHostController,
                    accountViewModel
                )
            }
            composable(SealedBottomNavItem.me.route) {
                MeScreen()
            }
            composable(Screens.addAccount.route) {
                AddAccountScreen(navHostController, accountViewModel)
            }
            composable(Screens.manageAccount.route) {
                ManageAccounts(accountViewModel,navHostController)
            }

            composable(
                route = "editAccount/{accountId}",
                arguments = listOf(navArgument("accountId") { type = NavType.IntType })
            ) { backStackEntry ->
                val accountId = backStackEntry.arguments?.getInt("accountId") ?: 0
                EditAccountScreen(
                    navController = navHostController,
                    accountViewModel = accountViewModel,
                    accountId = accountId
                )
            }
            composable(Screens.settings.route){
                ManageCategoryScreen(navHostController)
            }
            composable(Screens.addCateogryScreen.route){
                AddCategoryScreen()
            }
        }
    }
}

