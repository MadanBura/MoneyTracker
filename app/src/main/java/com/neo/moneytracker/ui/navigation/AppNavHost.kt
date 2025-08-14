package com.neo.moneytracker.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.neo.moneytracker.ui.components.BottomNavigationBar
import com.neo.moneytracker.ui.components.FabAddButton
import com.neo.moneytracker.ui.components.SearchSpec
import com.neo.moneytracker.ui.screens.*
import com.neo.moneytracker.ui.viewmodel.AccountsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navHostController: NavHostController) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val accountViewModel: AccountsViewModel = hiltViewModel()
    val isDialogVisible by accountViewModel.isDialogVisible.collectAsState()

    val showBottomBar = shouldShowBottomBar(currentRoute, isDialogVisible)

    Scaffold(
        bottomBar = { if (showBottomBar) BottomNavigationBar(navController = navHostController) },
        floatingActionButton = { if (showBottomBar) FabAddButton(navController = navHostController) },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = SealedBottomNavItem.records.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            appGraph(navHostController, accountViewModel)
        }
    }
}

private fun shouldShowBottomBar(route: String?, isDialogVisible: Boolean) =
    route in listOf(
        SealedBottomNavItem.records.route,
        SealedBottomNavItem.charts.route,
        SealedBottomNavItem.add.route,
        SealedBottomNavItem.reports.route,
        SealedBottomNavItem.me.route
    ) && !isDialogVisible

private fun NavGraphBuilder.appGraph(
    navController: NavHostController,
    accountViewModel: AccountsViewModel
) {
    composable(SealedBottomNavItem.records.route) {
        RecordScreen(navController, accountViewModel)
    }

    composable(Screens.searchScreen.route) {
        SearchSpec(navController)
    }

    composable(SealedBottomNavItem.charts.route) {
        ChartScreen()
    }

    composable(SealedBottomNavItem.add.route) {
        AddScreen(navController, accountViewModel)
    }

    composable(SealedBottomNavItem.reports.route) {
        ReportScreen(navController, accountViewModel)
    }

    composable(SealedBottomNavItem.me.route) {
        MeScreen()
    }

    composable(Screens.addAccount.route) {
        AddAccountScreen(navController, accountViewModel)
    }

    composable(Screens.manageAccount.route) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ManageAccounts(accountViewModel, navController)
        }
    }

    composable(
        route = "editAccount/{accountId}",
        arguments = listOf(navArgument("accountId") { type = NavType.IntType })
    ) { backStackEntry ->
        val accountId = backStackEntry.arguments?.getInt("accountId") ?: 0
        EditAccountScreen(navController, accountViewModel, accountId)
    }

    composable(Screens.settings.route) {
        ManageCategoryScreen(navController)
    }

    composable(Screens.addCateogryScreen.route) {
        AddCategoryScreen(navController)
    }

    composable(
        "add_screen?transactionId={transactionId}&isEdit={isEdit}",
        arguments = listOf(
            navArgument("transactionId") { type = NavType.IntType; defaultValue = -1 },
            navArgument("isEdit") { type = NavType.BoolType; defaultValue = false }
        )
    ) { backStackEntry ->
        val transactionId = backStackEntry.arguments
            ?.getInt("transactionId")
            ?.takeIf { it != -1 }
        val isEdit = backStackEntry.arguments?.getBoolean("isEdit") ?: false

        AddScreen(
            navController,
            accountViewModel,
            transactionId,
            isEdit
        )
    }
}
