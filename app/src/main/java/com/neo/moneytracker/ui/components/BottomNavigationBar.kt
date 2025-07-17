package com.neo.moneytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.neo.moneytracker.ui.navigation.SealedBottomNavItem
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.theme.YellowOrange

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        SealedBottomNavItem.records,
        SealedBottomNavItem.charts,
        SealedBottomNavItem.add,
        SealedBottomNavItem.reports,
        SealedBottomNavItem.me
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 4.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEachIndexed { index, item ->
            val isSelected = currentDestination?.route == item.route

            if (item == SealedBottomNavItem.add) {
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        if (!isSelected) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        }
                    },
                    icon = {
                        androidx.compose.foundation.layout.Box(
                            modifier = Modifier
                                .size(54.dp)
                                .background(
                                    color = LemonSecondary,
                                    shape = androidx.compose.foundation.shape.CircleShape
                                ),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.label,
                                modifier = Modifier.size(18.dp),
                                tint = Color.Black
                            )
                        }
                    },
                    label = { Text("") }
                )
            } else {
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.label,
                            modifier = Modifier.size(20.dp),
                            tint = if (isSelected) YellowOrange else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            color = if (isSelected) YellowOrange else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }
    }
}
