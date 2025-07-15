package com.neo.moneytracker.ui.navigation

sealed class Screens(val route: String) {
    object searchScreen : Screens("Search")
}