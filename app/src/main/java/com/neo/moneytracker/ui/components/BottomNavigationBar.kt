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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.neo.moneytracker.ui.navigation.SealedBottomNavItem


@Composable
fun BottomNavigationBar(navController: NavController) {

    val items = listOf(
        SealedBottomNavItem.records,
        SealedBottomNavItem.charts,
        SealedBottomNavItem.add,      // Center FAB-style icon
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
            if (item == SealedBottomNavItem.add) {
                // Center FAB-style icon
                NavigationBarItem(
                    selected = false, // You can style selected state if needed
                    onClick = {
                        if (currentDestination?.route != item.route) {
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
                                .size(48.dp)
                                .background(
                                    color = Light,
                                    shape = androidx.compose.foundation.shape.CircleShape
                                ),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.label,
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    label = { Text("") } // Optional: you can leave the label empty
                )
            } else {
                // Default navigation items
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.label,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                    label = { Text(text = item.label) },
                    selected = currentDestination?.route == item.route,
                    onClick = {
                        if (currentDestination?.route != item.route) {
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
