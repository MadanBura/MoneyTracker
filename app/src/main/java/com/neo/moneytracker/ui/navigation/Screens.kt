package com.neo.moneytracker.ui.navigation

sealed class Screens(val route: String) {
    object reports : Screens("reports")
    object records : Screens("records")
}