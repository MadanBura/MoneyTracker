package com.neo.moneytracker.ui.navigation

sealed class Screens(val route: String) {
    object settings : Screens("settings")
    object searchScreen : Screens("Search")

    object addAccount: Screens("AddAccount")
    object manageAccount: Screens("manageAccount")

}