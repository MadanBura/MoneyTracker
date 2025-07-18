package com.neo.moneytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.neo.moneytracker.ui.theme.LemonSecondary

@Composable
fun SimpleTabLayout(tabs: List<String>, onTabSelected: (String) -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .height(40.dp)
            .background(color = LemonSecondary)
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
    ) {
        tabs.forEachIndexed { index, tab ->
            val isSelected = index == selectedTab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(if (isSelected) Color.Black else LemonSecondary)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        selectedTab = index
                        onTabSelected(tab) // callback
                    }
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSelected) Color(0xFFFFEB3B) else Color.Black
                )
            }
        }
    }
}
