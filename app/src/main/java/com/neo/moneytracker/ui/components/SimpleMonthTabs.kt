package com.neo.moneytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SimpleMonthTabLayout(
    tabs: List<String>,
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEach { tab ->
            val isSelected = tab == selectedTab
            Text(
                text = tab,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTabSelected(tab) }
                    .background(if (isSelected) Color.Gray else Color.Transparent)
                    .padding(vertical = 8.dp),
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = Color.Black
            )
        }
    }
}
