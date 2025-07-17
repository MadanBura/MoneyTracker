package com.neo.moneytracker.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.neo.moneytracker.ui.navigation.SealedBottomNavItem
import com.neo.moneytracker.ui.theme.LemonSecondary


@Composable
fun FabAddButton(navController: NavController, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = {
            navController.navigate(SealedBottomNavItem.add.route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
            }
        },
        shape = CircleShape,
        backgroundColor = LemonSecondary,
        contentColor = Color.Black,
        modifier = modifier
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}